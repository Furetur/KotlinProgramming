package homeworks.server

import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import java.lang.IllegalArgumentException

class GameApplication {
    val server = GameServer()

    companion object {
        const val HOST = "127.0.0.1"
        const val PORT = 8080
    }

    private fun handleTurn(session: WebSocketServerSession, message: String) {
        val tokens = message.split(" ")
        if (tokens.size < 2) {
            throw IllegalArgumentException("Message does not contain the position")
        }
        val position = tokens[1].toIntOrNull() ?: throw IllegalArgumentException("Message's position must be Int")
        server.handleTurn(session, position)
    }

    private fun handleMessage(session: WebSocketServerSession, message: String) {
        println("Message received: $message")
        when {
            message.startsWith("turn") -> handleTurn(session, message)
        }
    }

    private fun handleConnect(session: WebSocketServerSession) {
        val playerData = server.playerConnect(session)
        if (playerData == null) {
            println("Client connected but was ignored")
        } else {
            println("Client connected and received id ${playerData.id}")
        }
        server.tryStartGame()
    }

    private fun handleDisconnect(session: WebSocketServerSession) {
        println("Client disconnected")
        server.playerDisconnect(session)
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
}
