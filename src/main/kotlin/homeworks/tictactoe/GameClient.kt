package homeworks.tictactoe

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import tornadofx.launch

@KtorExperimentalAPI
class GameClient {
    val client: HttpClient

    companion object {
        const val URL = "ws://localhost:8080/"
    }

    init {
        client = HttpClient {
            install(WebSockets)
        }
    }

    fun start() = coroutineScope {
        client.ws(
            method = HttpMethod.Get,
            host = "127.0.0.1",
            port = 8080,
            path = "/"
        ) {
            send(Frame.Text("dayum"))
            for (frame in incoming) {
                print("Received: $frame")
            }
        }
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