package homeworks.hw1

import homeworks.hw1.factorialIterative
import homeworks.hw1.factorialRecursive
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class _1_2KtTest {

    @Test
    fun factorialIterative_throwsExceptionOnNegativeNumbers() {
        try {
            factorialIterative(-1)
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {
            return
        }
    }

    @Test
    fun factorialIterative_calculatesZeroAndOneFactorialCorrectly() {
        assertEquals(1, factorialIterative(0))
    }

    @Test
    fun factorialIterative_calculatesOneFactorialCorrectly() {
        assertEquals(1, factorialIterative(1))
    }

    @Test
    fun factorialIterative_calculatesSimpleFactorials() {
        assertEquals(2 * 3 * 4 * 5 * 6, factorialIterative(6))
    }

    @Test
    fun `factorialIterative should calculate 8!`() {
        assertEquals(2 * 3 * 4 * 5 * 6 * 7 * 8 , factorialIterative(8))
    }

    @Test
    fun `factorialIterative should calculate 12!`() {
        assertEquals(2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 11 * 12, factorialIterative(12))
    }


    @Test
    fun factorialRecursive_throwsExceptionOnNegativeNumbers() {
        try {
            factorialRecursive(-1)
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {
            return
        }
    }

    @Test
    fun factorialRecursive_calculatesZeroFactorialCorrectly() {
        assertEquals(1, factorialRecursive(0))
    }

    @Test
    fun factorialRecursive_calculatesOneFactorialCorrectly() {
        assertEquals(1, factorialRecursive(1))
    }

    @Test
    fun factorialRecursive_calculatesSimpleFactorials() {
        assertEquals(2 * 3 * 4 * 5 * 6, factorialRecursive(6))
    }

    @Test
    fun `factorialRecursive should calculate 8!`() {
        assertEquals(2 * 3 * 4 * 5 * 6 * 7 * 8 , factorialRecursive(8))
    }

    @Test
    fun `factorialRecursive should calculate 12!`() {
        assertEquals(2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 * 10 * 11 * 12, factorialRecursive(12))
    }
}