package homeworks.tictactoewithserver.logic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LocalGameLoopTest {

    var tie = false
    var winner = -1
    var activePlayer = -1
    var lastTurnInfo = TurnInfo(-1, -1)

    var game = TestableGameLoop()

    data class TurnInfo(val playerId: Int, val position: Int)

    inner class TestableGameLoop : LocalGameLoop() {
        override fun onTie() {
            super.onTie()
            tie = true
        }

        override fun onVictory(playerId: Int) {
            super.onVictory(playerId)
            winner = playerId
        }

        override fun onTurnStart(playerId: Int) {
            super.onTurnStart(playerId)
            activePlayer = playerId
        }

        override fun onTurnMade(playerId: Int, position: Int) {
            super.onTurnMade(playerId, position)
            lastTurnInfo = TurnInfo(playerId, position)
        }
    }

    @BeforeEach
    fun initGameLoop() {
        game = TestableGameLoop()
        tie = false
        winner = -1
        activePlayer = -1
        lastTurnInfo = TurnInfo(-1, -1)
        game.onGameStart()
    }

    @Test
    fun `onGameStart should change active player to 0`() {
        assertEquals(0, activePlayer)
    }

    @Test
    fun `after turn active player should change to 1`() {
        game.makeTurn(0, 0)
        assertEquals(1, activePlayer)
    }

    @Test
    fun `winner should initially be -1`() {
        assertEquals(-1, winner)
    }

    @Test
    fun `tie should initially be false`() {
        assertFalse(tie)
    }

    @Test
    fun `make turn should throw if player is not active`() {
        assertThrows(GameLoop.PlayerCannotMakeTurn::class.java) {
            game.makeTurn(1, 0)
        }
    }

    @Test
    fun `make turn should throw if position is illegal`() {
        game.makeTurn(0, 0)
        assertThrows(GameLoop.IllegalTurnPosition::class.java) {
            game.makeTurn(1, 0)
        }
    }

    @Test
    fun `make turn should trigger onTurnMade`() {
        game.makeTurn(0, 0)
        game.makeTurn(1, 8)
        assertEquals(TurnInfo(1, 8), lastTurnInfo)
    }

    @Test
    fun `if player 0 wins it should trigger onWin`() {
        game.makeTurn(0, 0)
        game.makeTurn(1, 8)
        game.makeTurn(0, 1)
        game.makeTurn(1, 7)
        game.makeTurn(0, 2)
        assertEquals(0, winner)
    }
    @Test
    fun `if player 1 wins it should trigger onWin`() {
        game.makeTurn(0, 8)
        game.makeTurn(1, 0)
        game.makeTurn(0, 7)
        game.makeTurn(1, 1)
        game.makeTurn(0, 5)
        game.makeTurn(1, 2)
        assertEquals(1, winner)
    }

    @Test
    fun `if no one wins onTie should be triggered`() {
        game.makeTurn(0, 0)
        game.makeTurn(1, 2)
        game.makeTurn(0, 1)
        game.makeTurn(1, 3)
        game.makeTurn(0, 5)
        game.makeTurn(1, 4)
        game.makeTurn(0, 6)
        game.makeTurn(1, 8)
        game.makeTurn(0, 7)
        assert(tie)
    }
}
