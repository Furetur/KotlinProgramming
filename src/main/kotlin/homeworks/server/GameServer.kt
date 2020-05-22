package homeworks.server

import TicTacToeApp.Companion.FIELD_SIZE
import io.ktor.http.cio.websocket.Frame
import io.ktor.websocket.WebSocketServerSession
import kotlinx.coroutines.runBlocking
import java.lang.IllegalArgumentException

class GameServer {
    val field = MutableList(FIELD_SIZE) { -1 }
    val players = MutableList<PlayerData?>(2) { null }
    var gameOn = false

    val playersConnected: Int
        get() = players.count { it != null }

    fun getOtherPlayer(player: PlayerData): PlayerData? {
        val otherId = 1 - player.id
        return players[otherId]
    }

    fun getPlayerBySession(session: WebSocketServerSession): PlayerData? {
        return players.filterNotNull().find { it.session == session }
    }

    fun playerConnect(session: WebSocketServerSession): PlayerData? {
        if (getNumberOfConnectedPlayers() == 2) {
            return null
        }
        val freePlayerId = players.indexOf(null)
        val playerData = PlayerData(freePlayerId, session)
        players[freePlayerId] = playerData
        return playerData
    }

    fun playerDisconnect(playerData: PlayerData) {
        players[playerData.id] = null
        if (gameOn) {
            endGame()
        }
    }

    fun endGame() = runBlocking {
        gameOn = false
        for (playerData in players.filterNotNull()) {
            playerData.session.send(Frame.Text("gameEnded"))
        }
    }

    fun registerTurn(player: PlayerData, position: Int) {
        // check if position is valid
        if (field[position] != -1) {
            throw IllegalArgumentException("This position in field is already taken")
        }
        // update field
        field[position] = player.id
    }

    fun handleTurn(session: WebSocketServerSession, position: Int) {
        val player = getPlayerBySession(session)
        if (player != null) {
            registerTurn(player, position)
            // notify other user
            runBlocking {
                val otherPlayer = getOtherPlayer(player)
                if (otherPlayer?.session != null) {
                    otherPlayer.session.send(Frame.Text("turn ${player.id} $position"))
                }
            }
        }
    }
}
