package homeworks.server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.sessions
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.lang.IllegalArgumentException

class GameApplication {
    val server = GameServer()

    fun handleTurn(session: WebSocketServerSession, message: String) {
        val tokens = message.split(" ")
        if (tokens.size < 2) {
            throw IllegalArgumentException("Message does not contain the position")
        }
        val position = tokens[1].toIntOrNull() ?: throw IllegalArgumentException("Message's position must be Int")
        server.handleTurn(session, position)
    }

    fun handleMessage(session: WebSocketServerSession, message: String) {
        println("Message received: $message")
        when {
            message.startsWith("turn") -> handleTurn(session, message)
        }
    }

    fun handleConnect(session: WebSocketServerSession) {
        println("Client connected")
        server.playerConnect(session)
    }

    fun handleDisconnect(session: WebSocketServerSession) {
        server.playerDisconnect(session)
    }

    fun start() {
        embeddedServer(Netty, 8080) {
            install(WebSockets)
            routing {
                webSocket("/") {
                    try {
                        handleConnect(this)
                        for (frame in incoming) {
                            if (frame is Frame.Text) {
                                handleMessage(this, frame.readText())
                            }
                        }
                    } catch(e: ClosedReceiveChannelException) {
                        handleDisconnect(this)
                    }
                }
            }
        }.start(wait = true)
    }
}