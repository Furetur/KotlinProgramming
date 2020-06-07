package homeworks.logic

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

interface GameLoop {
    fun onGameStart()

    fun onTurnStart(playerId: Int)
    fun onTurnMade(playerId: Int, position: Int)
    fun onVictory(playerId: Int)
    fun onTie()
    fun onError(exception: Exception)

    fun makeTurn(playerId: Int, position: Int)

    class IllegalTurnPosition : IllegalArgumentException()

    class PlayerCannotMakeTurn : IllegalArgumentException()
}
