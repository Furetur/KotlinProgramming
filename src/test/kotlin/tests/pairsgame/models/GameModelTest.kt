package tests.pairsgame.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertFalse
import java.lang.IllegalArgumentException

internal class GameModelTest {

    companion object {
        const val SMALL_SIZE = 36

        // should be greater than 777
        const val BIG_SIZE = 1000
    }

    private var smallModel = GameModel(SMALL_SIZE)
    private var bigModel = GameModel(BIG_SIZE)

    private fun solve(field: List<Int>): List<Pair<Int, Int>> {
        val answers = mutableListOf<Pair<Int, Int>>()
        val values = field.distinct()
        for (value in values) {
            val leftIndex = field.indexOf(value)
            val rightIndex = field.lastIndexOf(value)
            answers.add(Pair(leftIndex, rightIndex))
        }
        return answers
    }

    @BeforeEach
    fun initModels() {
        smallModel = GameModel(SMALL_SIZE)
        bigModel = GameModel(BIG_SIZE)
    }

    @Test
    fun `should throw if size is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            GameModel(-1)
        }
    }

    @Test
    fun `should throw if size is odd`() {
        assertThrows(IllegalArgumentException::class.java) {
            GameModel(33)
        }
    }

    @Test
    fun `in the beginning game should be not over`() {
        assertFalse(smallModel.gameOver.value)
    }

    @Test
    fun `field should be of correct size`() {
        assertEquals(SMALL_SIZE, smallModel.field.size)
    }

    @Test
    fun `field should be of correct size 2`() {
        assertEquals(BIG_SIZE, bigModel.field.size)
    }

    @Test
    fun `small field should contain all pairs`() {
        val values = List(SMALL_SIZE / 2) { it }
        val expected = (values + values).toSet()
        assertEquals(expected, smallModel.field.toSet())
    }

    @Test
    fun `big field should contain all pairs`() {
        val values = List(BIG_SIZE / 2) { it }
        val expected = (values + values).toSet()
        assertEquals(expected, bigModel.field.toSet())
    }

    @Test
    fun `picking a button should add that button to the picked buttons list`() {
        smallModel.pickButton(0)
        assertEquals(listOf(0), smallModel.pickedButtons)
    }

    @Test
    fun `picking a button should add that button to the picked buttons list big`() {
        bigModel.pickButton(100)
        assertEquals(listOf(100), bigModel.pickedButtons)
    }

    @Test
    fun `picking button twice should not add anything to the picked buttons list`() {
        smallModel.pickButton(0)
        smallModel.pickButton(0)
        assertEquals(listOf(0), smallModel.pickedButtons)
    }

    @Test
    fun `picking button twice should not add anything to the picked buttons list big`() {
        bigModel.pickButton(100)
        bigModel.pickButton(100)
        assertEquals(listOf(100), bigModel.pickedButtons)
    }

    @Test
    fun `picking 3 buttons should reset the picked buttons list`() {
        smallModel.pickButton(0)
        smallModel.pickButton(10)
        smallModel.pickButton(15)
        assertEquals(listOf(15), smallModel.pickedButtons)
    }

    @Test
    fun `picking 3 buttons should reset the picked buttons list big`() {
        bigModel.pickButton(0)
        bigModel.pickButton(500)
        bigModel.pickButton(777)
        assertEquals(listOf(777), bigModel.pickedButtons)
    }

    @Test
    fun `picking 2 buttons with different values should not add them to the revealed list`() {
        smallModel.pickButton(0)
        smallModel.pickButton(10)
        smallModel.pickButton(15)
        assertEquals(listOf(15), smallModel.pickedButtons)
    }

    @Test
    fun `picking 2 buttons with the same value should add them to the revealed list`() {
        val answers = solve(smallModel.field)
        val (firstPosition, secondPosition) = answers[0]
        smallModel.pickButton(firstPosition)
        smallModel.pickButton(secondPosition)
        assertEquals(setOf(firstPosition, secondPosition), smallModel.revealedButtons.toSet())
    }

    @Test
    fun `picking 2 buttons with the same value should add them to the revealed list big`() {
        val answers = solve(bigModel.field)
        val (firstPosition, secondPosition) = answers[0]
        bigModel.pickButton(firstPosition)
        bigModel.pickButton(secondPosition)
        assertEquals(setOf(firstPosition, secondPosition), bigModel.revealedButtons.toSet())
    }

    @Test
    fun `game should be over when all buttons are picked`() {
        val answers = solve(smallModel.field)
        for ((leftPosition, rightPosition) in answers) {
            smallModel.pickButton(leftPosition)
            smallModel.pickButton(rightPosition)
        }
        assert(smallModel.gameOver.value)
    }

    @Test
    fun `game should be over when all buttons are picked big`() {
        val answers = solve(bigModel.field)
        for ((leftPosition, rightPosition) in answers) {
            bigModel.pickButton(leftPosition)
            bigModel.pickButton(rightPosition)
        }
        assert(bigModel.gameOver.value)
    }
}
