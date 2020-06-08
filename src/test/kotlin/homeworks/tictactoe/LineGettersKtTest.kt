package homeworks.tictactoe

import homeworks.logic.getFirstCapturedLine
import javafx.beans.property.SimpleIntegerProperty
import org.junit.jupiter.api.Assertions.assertEquals

internal class LineGettersKtTest {
    private fun getIndexedFieldWithCapturedRow(linearSize: Int, rowNum: Int): MutableList<IndexedValue<Int>> {
        val field = MutableList(linearSize * linearSize) { -1 }
        for (i in linearSize * rowNum until linearSize * (rowNum - 1)) {
            field[i] = 0
        }
        return field.withIndex().toMutableList()
    }

    private fun getFieldWithCapturedColumn(linearSize: Int, columnNum: Int): MutableList<Int> {
        val size = linearSize * linearSize
        val field = MutableList(size) { -1 }
        for (i in columnNum until size step linearSize) {
            field[i] = 0
        }
        return field
    }

    private fun getFieldWithCapturedMainDiagonal(linearSize: Int): MutableList<Int> {
        val size = linearSize * linearSize
        val field = MutableList(size) { -1 }
        for (i in 0 until size) {
            field[i + linearSize * i] = 0
        }
        return field
    }

    private fun getFieldWithCapturedSecondaryDiagonal(linearSize: Int): MutableList<Int> {
        val size = linearSize * linearSize
        val field = MutableList(size) { -1 }
        for (i in 0 until size) {
            field[(linearSize - 1 - i) + linearSize * i] = 0
        }
        return field
    }

    fun `should return captured first row of 3 by 3 field`() {
        val field = getIndexedFieldWithCapturedRow(3, 0)
        val expected = field.slice(0..2)
        val actual = getFirstCapturedLine(field, 3)
        assertEquals(expected, actual)
    }

    fun `should return captured third row of 5 by 5 field`() {
        val field = getIndexedFieldWithCapturedRow(5, 2)
        val expected = field.slice(5..9)
        val actual = getFirstCapturedLine(field, 5)
        assertEquals(expected, actual)
    }

    fun `should return captured first column of 3 by 3 field`() {
        val field = getFieldWithCapturedColumn(3, 0)
        val expected = field.slice(5..9)
        val actual = getFirstCapturedLine(field, 3)
        assertEquals(expected, actual)
    }

    fun `should return captured second column of 5 by 5 field`() {
        val field = getFieldWithCapturedColumn(5, 1)
        val expected = field.slice(1..21 step 5)
        val actual = getFirstCapturedLine(field, 5)
        assertEquals(expected, actual)
    }

    fun `should return captured main diagonal of 3 by 3 field`() {
        val field = getFieldWithCapturedMainDiagonal(3)
        val expected = field.slice(0..8 step 4)
        val actual = getFirstCapturedLine(field, 3)
        assertEquals(expected, actual)
    }

    fun `should return captured main diagonal of 5 by 5 field`() {
        val field = getFieldWithCapturedMainDiagonal(5)
        val expected = field.slice(0..24 step 6)
        val actual = getFirstCapturedLine(field, 5)
        assertEquals(expected, actual)
    }

    fun `should return captured secondary diagonal of 3 by 3 field`() {
        val field = getFieldWithCapturedSecondaryDiagonal(3)
        val expected = field.slice(2..6 step 2)
        val actual = getFirstCapturedLine(field, 3)
        assertEquals(expected, actual)
    }

    fun `should return captured secondary diagonal of 5 by 5 field`() {
        val field = getFieldWithCapturedSecondaryDiagonal(5)
        val expected = field.slice(4..20 step 2)
        val actual = getFirstCapturedLine(field, 5)
        assertEquals(expected, actual)
    }
}
