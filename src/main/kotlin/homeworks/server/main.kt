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

val field = MutableList(FIELD_SIZE) { -1 }
val players = MutableList<WebSocketServerSession?>(2) { null }
var playersConnected: Int = 0
var gameOn = false

fun getNumberOfConnectedPlayers(): Int {
    return players.filterNotNull().size
}

fun tryStartGame() = runBlocking {
    if (getNumberOfConnectedPlayers() == 2) {
        for ((playerId, session) in players.withIndex()) {
            if (session != null) {
                async { session.send(Frame.Text("gameStarted $playerId")) }
            }
        }
    }
}

fun handleConnect(session: WebSocketServerSession): Int? {
    // save
    if (getNumberOfConnectedPlayers() == 2) {
        return null
    }
    val freePlayerId = players.indexOf(null)
    players[freePlayerId] = session
    return freePlayerId
}

fun handleDisconnect(playerId: Int) {
    players[playerId] = null
    if (gameOn) {
        // end game and notify others
    }
}

fun endGame() = runBlocking{
    gameOn = false
    for (session in players) {
        if (session != null) {
            session.send(Frame.Text("gameEnded"))
        }
    }
}

fun getPlayerId(session: WebSocketServerSession): Int {
    return players.indexOf(session)
}

fun registerTurn(playerId: Int, position: Int) {
    // check if position is valid
    if (field[position] != -1) {
        throw IllegalArgumentException("This position in field is already taken")
    }
    // update field
    field[position] = playerId
}


fun handleTurn(playerSession: WebSocketServerSession, position: Int) {
    val playerId = getPlayerId(playerSession)
    if (playerId == -1) {
        throw IllegalArgumentException("User with this session is not in game")
    }

    // notify other user
    runBlocking {
        val otherPlayerId = 1 - playerId
        val otherSession = players[otherPlayerId]
        if (otherSession != null) {
            otherSession.send(Frame.Text("turn $playerId $position"))
        }
    }
}

fun handleMessage(message: ) {

}

fun main() {
    embeddedServer(Netty, 8080) {
        install(WebSockets)
        routing {
            webSocket("/") {
                call.sessions.
                println("Connected")
                for (frame in incoming) {
                    println("Received: $frame")
                    outgoing.send(Frame.Text("response"))
                }
            }
        }
    }.start(wait = true)
}
