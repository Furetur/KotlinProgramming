package homeworks.server

import TicTacToeApp.Companion.FIELD_LINEAR_SIZE
import TicTacToeApp.Companion.FIELD_SIZE
import io.ktor.http.cio.websocket.Frame
import io.ktor.websocket.WebSocketServerSession
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GameServer {
    private val field = MutableList(FIELD_SIZE) { -1 }
    private val players = MutableList<PlayerData?>(2) { null }
    var activePlayer = -1
    var gameOn = false

    private val playersConnected: Int
        get() = players.count { it != null }

    class PlayerData(val id: Int, val session: WebSocketServerSession)

    private fun getPlayerBySession(session: WebSocketServerSession): PlayerData? {
        return players.filterNotNull().find { it.session == session }
    }

    fun playerConnect(session: WebSocketServerSession): PlayerData? {
        if (playersConnected == 2) {
            return null
        }
        val freePlayerId = players.indexOf(null)
        val playerData = PlayerData(freePlayerId, session)
        players[freePlayerId] = playerData
        return playerData
    }

    fun tryStartGame() {
        if (playersConnected == 2) {
            startGame()
        }
    }

    fun playerDisconnect(session: WebSocketServerSession) {
        val playerData = getPlayerBySession(session)
        // player is not in game
            ?: return
        players[playerData.id] = null
        if (gameOn) {
            endGameBecauseOnePlayerDisconnected()
        }
    }

    private fun startGame() {
        gameOn = true
        activePlayer = 0
        notifyEach { "gameStarted ${it.id}" }
        println("Game started")
    }

    private fun endGameBecauseOnePlayerDisconnected() {
        gameOn = false
        activePlayer = -1
        for (i in 0 until FIELD_SIZE) {
            field[i] = -1
        }
        notifyAll("otherPlayerDisconnected")
        println("Ending the game: one player disconnected")
    }

    fun handleTurn(session: WebSocketServerSession, position: Int) {
        val player = getPlayerBySession(session)
        if (player != null && isTurnValid(player, position)) {
            field[position] = player.id
            activePlayer = 1 - activePlayer
            notifyAll("turn ${player.id} $position")
            println("Turn made by ${player.id} to position $position")
            println("Current field\n${field.chunked(FIELD_LINEAR_SIZE).joinToString("\n")}")
        }
    }

    private fun isTurnValid(player: PlayerData, position: Int): Boolean {
        return field[position] == -1 && activePlayer == player.id
    }

    private fun notifyAll(message: String) {
        notifyEach { message }
    }

    private fun notifyEach(messageBuilder: (PlayerData) -> String) = runBlocking {
        for (playerData in players.filterNotNull()) {
            val message = messageBuilder(playerData)
            launch { playerData.session.send(Frame.Text(message)) }
        }
    }
}
