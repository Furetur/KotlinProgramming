package homeworks.tictactoewithserver.tictactoe.models

import TicTacToeApp.Companion.FIELD_SIZE
import homeworks.tictactoewithserver.logic.GameLoop
import homeworks.tictactoewithserver.tictactoe.GameClient
import io.ktor.util.KtorExperimentalAPI
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import java.lang.Exception
import tornadofx.getValue
import tornadofx.setValue
import java.net.ConnectException

@KtorExperimentalAPI
class RemoteGameModel : GameLoop, GameModel {
    private val client = GameClient()

    private var thisPlayerId: Int = -1

    override val field = List(FIELD_SIZE) { SimpleIntegerProperty(-1) }

    override val activePlayerProperty = SimpleIntegerProperty(-1)
    override var activePlayer by activePlayerProperty

    override val winnerProperty = SimpleIntegerProperty(-1)
    override var winner by winnerProperty

    override val gameOverProperty = SimpleBooleanProperty(false)
    override var gameOver by gameOverProperty

    override val waitingProperty = SimpleBooleanProperty(true)
    override var waiting by waitingProperty

    override val errorMessageProperty = SimpleStringProperty(null)
    override var errorMessage by errorMessageProperty

    val connectedProperty = SimpleBooleanProperty(false)
    var connected by connectedProperty

    val gameStartedProperty = SimpleBooleanProperty(false)
    var gameStarted by gameStartedProperty

    init {
        client.onConnect = {
            connected = true
        }
        client.onGameStart = {
            thisPlayerId = it
            onGameStart()
        }
        client.onTurnMade = { playerId, position ->
            if (playerId != thisPlayerId) {
                onTurnMade(playerId, position)
            }
        }
        client.onVictory = {
            onVictory(it)
        }
        client.onTie = {
            onTie()
        }
        client.onConnectionError = {
            onError(ConnectionLostException())
        }
    }

    class ConnectionLostException : ConnectException()

    fun connect() {
        client.start()
    }

    fun disconnect() {
        client.close()
    }

    override fun onGameStart() {
        if (gameStarted) {
            return
        }
        gameStarted = true
        onTurnStart(0)
    }

    override fun makeTurn(position: Int) {
        makeTurn(activePlayer, position)
    }

    override fun onTurnStart(playerId: Int) {
        activePlayer = playerId
        waiting = activePlayer != thisPlayerId
    }

    override fun onTurnMade(playerId: Int, position: Int) {
        field[position].set(playerId)
        waiting = true
        onTurnStart(1 - playerId)
    }

    override fun onVictory(playerId: Int) {
        winner = playerId
        gameOver = true
    }

    override fun onTie() {
        gameOver = true
        winner = -1
    }

    override fun onError(exception: Exception) {
        errorMessage = "Error connecting"
    }

    override fun makeTurn(playerId: Int, position: Int) {
        client.makeTurn(position)
        onTurnMade(playerId, position)
    }
}
