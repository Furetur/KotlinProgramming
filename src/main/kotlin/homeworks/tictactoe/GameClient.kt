package homeworks.tictactoe

import homeworks.server.GameServer.Companion.HOST
import homeworks.server.GameServer.Companion.PORT
import homeworks.textmessages.GameEndedMessage
import homeworks.textmessages.GameStartedMessage
import homeworks.textmessages.TurnClientMessage
import homeworks.textmessages.TurnServerMessage
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.readText
import io.ktor.util.KtorExperimentalAPI
import io.ktor.http.cio.websocket.close
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tornadofx.runLater
import java.lang.IllegalArgumentException
import java.net.ConnectException

@KtorExperimentalAPI
class GameClient {
    val client: HttpClient = HttpClient {
        install(WebSockets)
    }

    var connected = false

    var session: WebSocketSession? = null
    var playerId: Int? = null

    var onConnect: (() -> Unit)? = null
    var onConnectionError: ((ConnectException) -> Unit)? = null
    var onDisconnect: (() -> Unit)? = null
    var onGameStart: ((Int) -> Unit)? = null
    var onTurnMade: ((Int, Int) -> Unit)? = null
    var onTie: (() -> Unit)? = null
    var onVictory: ((Int) -> Unit)? = null

    fun start() {
        GlobalScope.launch {
            try {
                connect()
            } catch (e: ConnectException) {
                runLater {
                    onConnectionError?.let { it(e) }
                }
            }
        }
    }

    private suspend fun connect() {
        client.ws(
            method = HttpMethod.Get,
            host = HOST,
            port = PORT,
            path = "/"
        ) {
            session = this
            runLater {
                connected = true
                onConnect?.let { it() }
            }
            try {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        handleMessage(frame.readText())
                    }
                }
            } finally {
                runLater {
                    connected = false
                    onDisconnect?.let { it() }
                }
            }
        }
    }

    fun close() {
        runBlocking {
            session?.close()
        }
    }

    private fun handleMessage(message: String) {
        try {
            when {
                message.startsWith(GameStartedMessage.name) -> handleGameStart(message)
                message.startsWith(TurnServerMessage.name) -> handleTurn(message)
                message.startsWith(GameEndedMessage.name) -> handleGameEnd(message)
            }
        } catch (e: IllegalArgumentException) {
            println("Error during message handling occured: ${e.message}")
        }
    }

    private fun handleGameStart(message: String) {
        val gameStartedMessage = GameStartedMessage(message)
        runLater {
            onGameStart?.let { it(gameStartedMessage.playerId) }
        }
    }

    private fun handleGameEnd(message: String) {
        val gameEndedMessage = GameEndedMessage(message)
        runLater {
            if (gameEndedMessage.winner == -1) {
                onTie?.let { it() }
            } else {
                onVictory?.let { it(gameEndedMessage.winner) }
            }
        }
    }

    private fun handleTurn(message: String) {
        val turnMessage = TurnServerMessage(message)

        runLater {
            onTurnMade?.let { it(turnMessage.playerId, turnMessage.position) }
        }
    }

    fun makeTurn(position: Int) = GlobalScope.launch {
        session?.let {
            it.send(Frame.Text(TurnClientMessage.compose(position)))
        } ?: throw IllegalStateException("The client have not yet connected to the server")
    }
}
