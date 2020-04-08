package hw1.homeworks.hw1

import homeworks.hw1.countSubstringOccurences
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class _1_3KtTest {
    @Test
    fun `should throw exception if childString is empty`() {
        try {
            countSubstringOccurences("asd", "")
            fail("Expected an IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            // do nothing
        }
    }

    @Test
    fun `should return 0 if parent string is empty`() {
        assertEquals(0, countSubstringOccurences("", "a"))
    }

    @Test
    fun `should return 1 if strings are equal`() {
        assertEquals(1, countSubstringOccurences("abcdef", "abcdef"))
    }

    @Test
    fun `should work correctly with strings that consist of 1 repeating character`() {
        assertEquals(8, countSubstringOccurences("aaaaaaaaa", "aa"))
    }

    @Test
    fun `should work for some strings 1`() {
        assertEquals(1, countSubstringOccurences("abc", "ab"))
    }

    @Test
    fun `should work for some strings 2`() {
        assertEquals(2, countSubstringOccurences("ababc", "ab"))
    }

    @Test
    fun `should work for some strings 3`() {
        assertEquals(2, countSubstringOccurences("abccbaabc", "abc"))
    }

    @Test
    fun `should work for long strings 1`() {
        val testString =
            "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc"
        assertEquals(72, countSubstringOccurences(testString, "abc"))
    }
    @Test
    fun `should work for long strings 2`() {
        val testString =
            "1231232132131ajdhkashdaskdjas_xxx_1231232132131ajdhkashdaskdjas_xxx_1231232132131ajdhkashdaskdjas_xxx_1231232132131ajdhkashdaskdjas_xxx_"
        assertEquals(4, countSubstringOccurences(testString, "xxx"))
    }
}