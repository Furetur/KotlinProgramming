package homeworks.server

import homeworks.logic.GameLoop
import homeworks.textmessages.TextMessage
import homeworks.textmessages.TurnClientMessage
import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class GameServer {

    private val playersManager = PlayersManager()
    var game: ServerGameLoop = ServerGameLoop(playersManager)

    companion object {
        const val HOST = "127.0.0.1"
        const val PORT = 8080
    }

    fun start() {
        embeddedServer(Netty, PORT) {
            install(WebSockets)
            routing {
                webSocket("/") {
                    handleConnect(this)
                    try {
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                handleMessage(this, frame.readText())
                            }
                        }
                    } finally {
                        handleDisconnect(this)
                    }
                }
            }
        }.start(wait = true)
    }

    private fun handleConnect(session: WebSocketServerSession) {
        if (playersManager.isFull) {
            println("Player connected but was kicked")
            GlobalScope.launch {
                disconnectClient(session)
            }
        } else {
            val playerId = playersManager.connectPlayer(session)
            println("Player connected and received id=$playerId")
            if (playersManager.isFull) {
                game.onGameStart()
            }
        }
    }

    private suspend fun disconnectClient(session: WebSocketServerSession) {
        println("Disconnecting client")
        session.close()
    }

    private fun handleDisconnect(session: WebSocketServerSession) {
        if (!playersManager.isPlayerConnected(session)) {
            println("Client disconnected - they were not in the lobby")
            return
        }
        val playerId = playersManager.forgetPlayer(session)
        println("Player #$playerId disconnected")
        if (game.gameOn) {
            game.onPlayerDisconnected(playerId)
        }
    }

    private fun handleMessage(session: WebSocketServerSession, message: String) {
        println("Message received: $message")
        try {
            if (message.startsWith(TurnClientMessage.name)) {
                handleTurnMessage(session, message)
            }
        } catch (e: TextMessage.IllegalMessageTypeException) {
            println("Received unsupported message")
        } catch (e: TextMessage.IllegalNumberOfMessageArgumentsException) {
            println("Received message with an illegal number of arguments")
        } catch (e: TextMessage.IllegalMessageArgumentSyntax) {
            println("Received message with illegal arguments: ${e.message}")
        } catch (e: PlayerNotInLobbyException) {
            println("Received turn message from player who is not in lobby")
        } catch (e: GameLoop.PlayerCannotMakeTurn) {
            println("Received turn message from the player who cannot make a turn")
        } catch (e: GameLoop.IllegalTurnPosition) {
            println("Received turn message to an illegal position")
        }
    }

    private fun handleTurnMessage(session: WebSocketServerSession, message: String) {
        val turnMessage = TurnClientMessage(message)
        if (!playersManager.isPlayerConnected(session)) {
            throw PlayerNotInLobbyException()
        }
        val playerId = playersManager.getPlayerId(session)
        game.makeTurn(playerId, turnMessage.position)
    }

    inner class PlayersManager {
        private val players: MutableList<WebSocketServerSession?> = MutableList(2) { null }
        val isFull: Boolean
            get() = !players.contains(null)
        private val connectedPlayersIds: List<Int>
            get() = players.withIndex().filter { it.value != null }.map { it.index }

        fun connectPlayer(session: WebSocketServerSession): Int {
            if (isFull) {
                throw LobbyIsFullException()
            }
            val playerId = players.indexOf(null)
            players[playerId] = session
            return playerId
        }

        fun getPlayerId(session: WebSocketServerSession): Int {
            return players.indexOf(session)
        }

        fun isPlayerConnected(session: WebSocketServerSession): Boolean {
            return players.indexOf(session) != -1
        }

        fun forgetPlayer(session: WebSocketServerSession): Int {
            val playerId = players.indexOf(session)
            if (playerId == -1) {
                throw IllegalArgumentException("Player not in game")
            }
            players[playerId] = null
            return playerId
        }

        fun notifyAll(message: String) {
            notifyEach { message }
        }

        fun notifyEach(messageBuilder: (Int) -> String) = runBlocking {
            for (playerId in connectedPlayersIds) {
                val message = messageBuilder(playerId)
                println("Sending message='$message' to player #$playerId")
                launch { players[playerId]?.send(Frame.Text(message)) }
            }
        }

        suspend fun kick(playerId: Int) {
            val session = players[playerId] ?: throw PlayerNotInLobbyException()
            println("Kicking player #$playerId")
            forgetPlayer(session)
            disconnectClient(session)
        }

        fun kickAll() {
            println("Kicking all players")
            for (playerId in connectedPlayersIds) {
                GlobalScope.launch { kick(playerId) }
            }
        }
    }

    class LobbyIsFullException : IllegalStateException()

    class PlayerNotInLobbyException : IllegalArgumentException()
}
