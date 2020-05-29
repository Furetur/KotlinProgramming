package homeworks.server

import TicTacToeApp.Companion.FIELD_SIZE
import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import tornadofx.launch
import java.lang.IllegalArgumentException

fun main() {
    val application = GameApplication()
    application.start()
}
