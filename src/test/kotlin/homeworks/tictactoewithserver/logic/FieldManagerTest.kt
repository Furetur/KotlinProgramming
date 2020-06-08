package homeworks.tictactoewithserver.logic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FieldManagerTest {
    private fun getFieldWithCapturedRowBy(linearSize: Int, rowId: Int, playerId: Int): FieldManager {
        val field = MutableList(linearSize * linearSize) { -1 }
        for (i in linearSize * rowId until linearSize * (rowId + 1)) {
            field[i] = playerId
        }
        return FieldManager(field, linearSize)
    }

    private fun getFieldWithCapturedColumnBy(linearSize: Int, columnId: Int, playerId: Int): FieldManager {
        val size = linearSize * linearSize
        val field = MutableList(size) { -1 }
        for (i in columnId until size step linearSize) {
            field[i] = playerId
        }
        return FieldManager(field, linearSize)
    }

    private fun getFieldWithCapturedMainDiagonal(linearSize: Int, playerId: Int): FieldManager {
        val size = linearSize * linearSize
        val field = MutableList(size) { -1 }
        for (i in 0 until linearSize) {
            field[i + linearSize * i] = playerId
        }
        return FieldManager(field, linearSize)
    }

    private fun getFieldWithCapturedSecondaryDiagonal(linearSize: Int, playerId: Int): FieldManager {
        val size = linearSize * linearSize
        val field = MutableList(size) { -1 }
        for (i in 0 until linearSize) {
            field[(linearSize - 1 - i) + linearSize * i] = playerId
        }
        return FieldManager(field, linearSize)
    }

    // rows
    @Test
    fun `should return winner if first row of 3 by 3 field is captured`() {
        val field = getFieldWithCapturedRowBy(3, 0, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if first row of 10 by 10 field is captured`() {
        val field = getFieldWithCapturedRowBy(10, 0, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if third row of 10 by 10 field is captured`() {
        val field = getFieldWithCapturedRowBy(10, 3, 1)
        val expected = 1
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    // columns
    @Test
    fun `should return winner if first column of 3 by 3 field is captured`() {
        val field = getFieldWithCapturedColumnBy(3, 0, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if first column of 10 by 10 field is captured`() {
        val field = getFieldWithCapturedColumnBy(10, 0, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if third column of 10 by 10 field is captured`() {
        val field = getFieldWithCapturedColumnBy(10, 3, 1)
        val expected = 1
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    // secondary diagonal

    @Test
    fun `should return winner if secondary diagonal of 3 by 3 field is captured`() {
        val field = getFieldWithCapturedSecondaryDiagonal(3, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if secondary diagonal of 10 by 10 field is captured`() {
        val field = getFieldWithCapturedSecondaryDiagonal(10, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if secondary diagonal of 100 by 100 field is captured`() {
        val field = getFieldWithCapturedSecondaryDiagonal(100, 1)
        val expected = 1
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    // main diagonals

    @Test
    fun `should return winner if main diagonal of 3 by 3 field is captured`() {
        val field = getFieldWithCapturedMainDiagonal(3, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if main diagonal of 10 by 10 field is captured`() {
        val field = getFieldWithCapturedMainDiagonal(10, 0)
        val expected = 0
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }

    @Test
    fun `should return winner if main diagonal of 100 by 100 field is captured`() {
        val field = getFieldWithCapturedMainDiagonal(100, 1)
        val expected = 1
        val actual = field.getWinner()
        assertEquals(expected, actual)
    }
}
