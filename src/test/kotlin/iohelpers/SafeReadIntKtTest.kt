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
    fun safeParseInt_throwsNumberFormatExceptionOnWrongString1() {
        try {
            safeParseInt("1.0abc")
            fail("Expected a NumberFormatException to be thrown")
        } catch (e: NumberFormatException) {
            return
        }
    }


    @Test
    fun safeParseInt_throwsNumberFormatExceptionOnWrongString2() {
        try {
            safeParseInt("1.3456787654334567765434567876545678a")
            fail("Expected a NumberFormatException to be thrown")
        } catch (e: NumberFormatException) {
            return
        }
    }


    @Test
    fun safeParseInt_throwsNumberFormatExceptionOnWrongString3() {
        try {
            safeParseInt("3456789098d3456789")
            fail("Expected a NumberFormatException to be thrown")
        } catch (e: NumberFormatException) {
            return
        }
    }


    @Test
    fun safeParseInt_correctlyParsesSimpleInteger1() {
        val number: Int = safeParseInt("123")
        assertEquals(number, 123)
    }


    @Test
    fun safeParseInt_correctlyParsesSimpleInteger2() {
        val number: Int = safeParseInt("1166109")
        assertEquals(number, 1166109)
    }


    @Test
    fun safeParseInt_correctlyParsesSimpleInteger3() {
        val number: Int = safeParseInt("44944464")
        assertEquals(number, 44944464)
    }


    @Test
    fun safeParseInt_cutsZerosAtTheStart1() {
        val number: Int = safeParseInt("0006686")
        assertEquals(number, 6686)
    }


    @Test
    fun safeParseInt_cutsZerosAtTheStart2() {
        val number: Int = safeParseInt("00106686")
        assertEquals(number, 106686)
    }
}