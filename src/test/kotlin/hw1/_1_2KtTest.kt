package hw1

import factorialIterative
import factorialRecursive
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
}