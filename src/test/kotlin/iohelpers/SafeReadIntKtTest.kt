package iohelpers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

internal class SafeReadIntKtTest {

    @Test
    fun safeParseInt_throwsOnNullString() {
        try {
            safeParseInt(null)
            fail("Expected to throw an IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            return
        }
    }

    @Test
    fun safeParseInt_throwsNumberFormatExceptionOnWrongString() {
        try {
            safeParseInt("1.0abc")
            fail("Expected a NumberFormatException tp be thrown")
        } catch (e: NumberFormatException) {
            return
        }
    }

    @Test
    fun safeParseInt_correctlyParsesSimpleInteger() {
        val number: Int = safeParseInt("123")
        assertEquals(number, 123)
    }
}