package homeworks.tictactoe

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.readText
import io.ktor.util.KtorExperimentalAPI
import java.lang.IllegalArgumentException

@KtorExperimentalAPI
class GameClient {
    val client: HttpClient
    var session: WebSocketSession? = null
    var playerId: Int? = null

    companion object {
        const val URL = "ws://localhost:8080/"
    }

    init {
        client = HttpClient {
            install(WebSockets)
        }
    }

    suspend fun start() {
        client.ws(
            method = HttpMethod.Get,
            host = "127.0.0.1",
            port = 8080,
            path = "/"
        ) {
            session = this
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    handleMessage(frame.readText())
                }
            }
        }
    }

    fun handleMessage(message: String) {
        when {
            message.startsWith("gameStarted") ->
        }
    }

    fun handleGameStart(message: String) {
        val tokens = message.split(" ")
        if (tokens.size < 2) {
            throw IllegalArgumentException("Expected player id in gameStart message")
        }
        val receivedPlayerId = tokens[1].toIntOrNull()
            ?: throw IllegalArgumentException("Expected player id of type Int in gameStart message")
        playerId = receivedPlayerId
    }

    fun onTurn() {

    }

    fun makeTurn(position: Int) {

    }

    fun onGameStart() {

    }

    fun onGameEnd() {

    }

    fun onConnect() {

    }

    fun onConnectionError() {

    }


    fun onConnectionLost() {

    }

}