package homeworks.tictactoe

import homeworks.server.GameApplication.Companion.HOST
import homeworks.server.GameApplication.Companion.PORT
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
    var session: WebSocketSession? = null
    var playerId: Int? = null

    var onConnect: (() -> Unit)? = null
    var onConnectionError: ((ConnectException) -> Unit)? = null
    var onDisconnect: (() -> Unit)? = null
    var onGameStart: ((Int) -> Unit)? = null
    var onOtherPlayerDisconnected: (() -> Unit)? = null
    var onTurn: ((Int, Int) -> Unit)? = null

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
                message.startsWith("gameStarted") -> handleGameStart(message)
                message.startsWith("otherPlayerDisconnected") -> runLater {
                    onOtherPlayerDisconnected?.let { it() }
                }
                message.startsWith("turn") -> handleTurn(message)
            }
        } catch (e: IllegalArgumentException) {
            println("Error during message handling occured: ${e.message}")
        }
    }

    private fun handleGameStart(message: String) {
        val tokens = message.split(" ")
        if (tokens.size < 2) {
            throw IllegalArgumentException("Expected player id in gameStart message")
        }
        val receivedPlayerId = tokens[1].toIntOrNull()
            ?: throw IllegalArgumentException("Expected player id of type Int in gameStart message")
        playerId = receivedPlayerId
        runLater {
            onGameStart?.let { it(receivedPlayerId) }
        }
    }

    private fun handleTurn(message: String) {
        val tokens = message.split(" ")
        if (tokens.size <= 2) {
            throw IllegalArgumentException("Expected message 'turn <player> <position>' but received $message")
        }
        val receivedPlayerId = tokens[1].toIntOrNull()
        val receivedPosition = tokens[2].toIntOrNull()

        if (receivedPlayerId == null || receivedPosition == null) {
            throw IllegalArgumentException("One of the arguments of turn message was not int")
        }

        runLater {
            onTurn?.let { it(receivedPlayerId, receivedPosition) }
        }
    }

    fun makeTurn(position: Int) = GlobalScope.launch {
        session?.let {
            if (playerId == null) {
                throw IllegalStateException("This player has not yet joined a game")
            }
            it.send(Frame.Text("turn $position"))
        } ?: throw IllegalStateException("The client have not yet connected to the server")
    }
}
