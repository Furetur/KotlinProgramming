package homeworks.tictactoe.models

import TicTacToeApp.Companion.FIELD_LINEAR_SIZE
import TicTacToeApp.Companion.FIELD_SIZE
import homeworks.tictactoe.getFirstCapturedLine
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.setValue
import tornadofx.getValue
import java.lang.IllegalArgumentException

open class GameWithFriendModel {
    val field = List(FIELD_SIZE) { SimpleIntegerProperty(-1) }

    val activePlayerProperty = SimpleIntegerProperty(-1)
    var activePlayer by activePlayerProperty

    private val winnerProperty = SimpleIntegerProperty(-1)
    var winner by winnerProperty

    val gameOverProperty = SimpleBooleanProperty(false)
    var gameOver by gameOverProperty

    val waitingProperty = SimpleBooleanProperty(true)
    var waiting by waitingProperty

    val errorMessageProperty = SimpleStringProperty(null)
    var errorMessage by errorMessageProperty

    fun startGame() {
        activePlayer = 0
        startTurn()
    }

    open fun startTurn() {
        waiting = false
    }

    fun makeTurn(property: SimpleIntegerProperty) {
        val position = field.indexOf(property)
        if (position == -1) {
            throw IllegalArgumentException("This property is not in the field")
        }
        makeTurn(position)
    }

    fun makeTurn(position: Int) {
        if (field[position].value != -1) {
            throw IllegalArgumentException("This position is already taken")
        }
        waiting = true
        commitTurn(position)
        checkIfGameEnded()
        if (!gameOver) {
            updateActivePlayer()
            startTurn()
        }
    }

    open fun commitTurn(position: Int) {
        field[position].set(activePlayer)
    }

    private fun checkIfGameEnded() {
        checkVictory()
        if (isWholeFieldOccupied(field)) {
            gameOver = true
        }
    }

    private fun isWholeFieldOccupied(field: List<SimpleIntegerProperty>): Boolean {
        return field.none { it.value == -1 }
    }

    private fun checkVictory() {
        val capturedLine = getFirstCapturedLine(field, FIELD_LINEAR_SIZE)
        if (capturedLine != null) {
            winner = capturedLine[0].value
            gameOver = true
        }
    }

    private fun updateActivePlayer() {
        activePlayer = (activePlayer + 1) % 2
    }
}
