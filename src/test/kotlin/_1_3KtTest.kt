package hw1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class _1_3KtTest {
    @Test
    fun countSubstring_throwsExceptionIfChildStringIsEmpty() {
        try {
            countSubstring("asd", "")
            fail("Expected an IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // do nothing
        }
    }

    @Test
    fun countSubstring_returnsZeroIfParentStringIsEmpty() {
        assertEquals(0, countSubstring("", "a"))
    }

    @Test
    fun countSubstring_returnsOneOnEqualStrings() {
        assertEquals(1, countSubstring("abcdef", "abcdef"))
    }

    @Test
    fun countSubstring_worksCorrectlyWithStringsOfOneSymbol() {
        assertEquals(8, countSubstring("aaaaaaaaa", "aa"))
    }

    @Test
    fun countSubstring_returnsCorrectValuesForSomeStrings1() {
        assertEquals(1, countSubstring("abc", "ab"))
    }
    @Test
    fun countSubstring_returnsCorrectValuesForSomeStrings2() {
        assertEquals(2, countSubstring("ababc", "ab"))
    }
    @Test
    fun countSubstring_returnsCorrectValuesForSomeStrings3() {
        assertEquals(2, countSubstring("abccbaabc", "abc"))
    }
}