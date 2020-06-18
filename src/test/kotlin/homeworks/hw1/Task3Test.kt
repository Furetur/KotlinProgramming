package hw1.homeworks.hw1

import homeworks.hw1.countSubstringOccurrences
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class Task3Test {
    @Test
    fun `should throw exception if childString is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            countSubstringOccurrences("asd", "")
        }
    }

    @Test
    fun `should return 0 if parent string is empty`() {
        assertEquals(0, countSubstringOccurrences("", "a"))
    }

    @Test
    fun `should return 1 if strings are equal`() {
        assertEquals(1, countSubstringOccurrences("abcdef", "abcdef"))
    }

    @Test
    fun `should work correctly with strings that consist of 1 repeating character`() {
        assertEquals(8, countSubstringOccurrences("aaaaaaaaa", "aa"))
    }

    @Test
    fun `should work for some strings 1`() {
        assertEquals(1, countSubstringOccurrences("abc", "ab"))
    }

    @Test
    fun `should work for some strings 2`() {
        assertEquals(2, countSubstringOccurrences("ababc", "ab"))
    }

    @Test
    fun `should work for some strings 3`() {
        assertEquals(2, countSubstringOccurrences("abccbaabc", "abc"))
    }

    @Test
    fun `should work for long strings 1`() {
        val testString =
            "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc"
        assertEquals(72, countSubstringOccurrences(testString, "abc"))
    }
    @Test
    fun `should work for long strings 2`() {
        val testString =
            "1231232132131ajdhkashdaskdjas_xxx_1231232132131ajdhkashdaskdjas_xxx_1231232132131ajdhkashdaskdjas_xxx_1231232132131ajdhkashdaskdjas_xxx_"
        assertEquals(4, countSubstringOccurrences(testString, "xxx"))
    }
}
