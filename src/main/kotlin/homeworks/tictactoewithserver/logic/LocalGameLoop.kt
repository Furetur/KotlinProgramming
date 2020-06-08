package homeworks.tictactoewithserver.logic

import TicTacToeApp.Companion.FIELD_LINEAR_SIZE
import TicTacToeApp.Companion.FIELD_SIZE
import java.lang.Exception

open class LocalGameLoop : GameLoop {
    private val field = MutableList(FIELD_SIZE) { -1 }
    private var activePlayer = -1
    private var gameEnded = false
    private var winner = -1

    override fun onGameStart() {
        field.replaceAll { -1 }
        gameEnded = false
        activePlayer = -1
        onTurnStart(0)
    }

    override fun onTurnStart(playerId: Int) {
        activePlayer = playerId
    }

    override fun onTurnMade(playerId: Int, position: Int) {
        field[position] = playerId
    }

    private fun checkIfGameEnded() {
        val victoriousPlayer = getWinner(field, FIELD_LINEAR_SIZE)
        if (victoriousPlayer != null) {
            onVictory(victoriousPlayer)
        } else {
            // if the whole field is occupied
            if (field.none { it == -1 }) {
                onTie()
            }
        }
    }

    private fun getNextPlayer(): Int {
        return 1 - activePlayer
    }

    override fun onVictory(playerId: Int) {
        winner = playerId
        gameEnded = true
    }

    override fun onTie() {
        winner = -1
        gameEnded = true
    }

    override fun onError(exception: Exception) {
        TODO("Not yet implemented")
    }

    override fun makeTurn(playerId: Int, position: Int) {
        if (activePlayer != playerId) {
            throw GameLoop.PlayerCannotMakeTurn()
        }
        if (field[position] != -1) {
            throw GameLoop.IllegalTurnPosition()
        }
        onTurnMade(playerId, position)
        checkIfGameEnded()
        if (!gameEnded) {
            onTurnStart(getNextPlayer())
        }
    }
}
