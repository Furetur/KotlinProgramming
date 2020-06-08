package homeworks.tictactoewithserver.tictactoe.bots

import javafx.beans.property.SimpleIntegerProperty
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class HardBotTest {
    private val field = List(9) { SimpleIntegerProperty(-1) }
    private var firstBot = HardBot(field, 1, 0)
    private var secondBot = HardBot(field, 0, 1)

    @BeforeEach
    fun clearEverything() {
        for (i in 0..8) {
            field[i].set(-1)
        }
        firstBot = HardBot(field, 1, 0)
        secondBot = HardBot(field, 0, 1)
    }

    @Test
    fun `bot should occupy the middle cell on its first turn`() {
        val middleCell = field[4]
        val botTurn = firstBot.makeTurn()

        assertEquals(middleCell, botTurn)
    }

    @Test
    fun `bot should occupy the middle cell if its not taken`() {
        field[0].set(0)
        val middleCell = field[4]
        val botTurn = secondBot.makeTurn()

        assertEquals(middleCell, botTurn)
    }

    @Test
    fun `bot should win if it can 1`() {
        field[0].set(0)
        field[1].set(0)
        val cell = field[2]
        val botTurn = firstBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `bot should win if it can 2`() {
        field[3].set(0)
        field[4].set(0)
        val cell = field[5]
        val botTurn = firstBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `bot should win if it can 3`() {
        field[0].set(0)
        field[4].set(0)
        val cell = field[8]
        val botTurn = firstBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `priority for winning should be higher than priority for not letting the user win 1`() {
        field[0].set(0)
        field[1].set(0)
        field[3].set(1)
        field[4].set(1)
        val cell = field[2]
        val botTurn = firstBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `priority for winning should be higher than priority for not letting the user win 2`() {
        field[0].set(0)
        field[1].set(0)
        field[6].set(1)
        field[7].set(1)
        val cell = field[2]
        val botTurn = firstBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `bot should not let the user win 1`() {
        field[0].set(0)
        field[1].set(0)
        val cell = field[2]
        val botTurn = secondBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `bot should not let the user win 2`() {
        field[3].set(0)
        field[4].set(0)
        val cell = field[5]
        val botTurn = secondBot.makeTurn()

        assertEquals(cell, botTurn)
    }

    @Test
    fun `bot should not let the user win 3`() {
        field[2].set(0)
        field[4].set(0)
        val cell = field[6]
        val botTurn = secondBot.makeTurn()

        assertEquals(cell, botTurn)
    }
}
