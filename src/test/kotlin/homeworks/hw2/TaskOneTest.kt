package homeworks.hw2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.lang.IllegalArgumentException

internal class TaskOneTest {

    private fun convertMatchesToList(sequence: Sequence<MatchResult>): List<String> {
        return sequence.map { it.value }.toList()
    }

    private fun generateBigTest(patternUnit: String, size: Int, repeatsAtLeast: Int = 3):
            Triple<String, List<String>, Int> {
        val matches = mutableListOf<String>()
        var expectedCharsNum = 0
        var str = ""
        var curPattern = ""
        for (i in 1..size) {
            curPattern += patternUnit
            str += " $curPattern"
            if (i >= repeatsAtLeast) {
                matches.add(curPattern)
                expectedCharsNum += (i - repeatsAtLeast + 1) * patternUnit.length
            }
        }
        return Triple(str, matches, expectedCharsNum)
    }

    // findAllRepeatingPatterns

    @Test
    fun `should throw if repeatsAtLeast is zero`() {
        assertThrows(IllegalArgumentException::class.java) {
            findAllRepeatingPatterns("x", 0, "xxxxxxxxxxx xxxxx")
        }
    }

    @Test
    fun `should throw if repeatsAtLeast is less than zero`() {
        assertThrows(IllegalArgumentException::class.java) {
            findAllRepeatingPatterns("x", -1, "xxxxxxxxxxx xxxxx")
        }
    }

    @Test
    fun `should throw if pattern is empty`() {
        assertThrows(IllegalArgumentException::class.java) {
            findAllRepeatingPatterns("", 3, "xxxxxxxxxxx xxxxx")
        }
    }

    @Test
    fun `should return empty sequence on an empty string`() {
        val str = ""
        val expected = listOf<String>()
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 1, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should return empty sequence if nothing matches the pattern`() {
        val str = "abcabc"
        val expected = listOf<String>()
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 1, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should return empty sequence if pattern repeats too few times`() {
        val str = "abc abcabc abcabcabc"
        val expected = listOf<String>()
        val actual = convertMatchesToList(findAllRepeatingPatterns("abc", 4, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should match all 3 letters if repetitions number is 1`() {
        val str = "xxx"
        val expected = listOf("xxx")
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 1, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should match all 3 letters in a 3 letter sequence if repetitions number is 2`() {
        val str = "xxx"
        val expected = listOf("xxx")
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 2, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should match xxx and xxxx`() {
        val str = "xx and xxx and xxxx"
        val expected = listOf("xxx", "xxxx")
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 3, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should match xx and xxx and xxxx`() {
        val str = "xx and xxx and xxxx"
        val expected = listOf("xx", "xxx", "xxxx")
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 2, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should match long xxxxxxxx`() {
        val longXxx = "x".repeat(100)
        val testString = "test $longXxx end"
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 3, testString))
        assertEquals(listOf(longXxx), actual)
    }

    @Test
    fun `should match all xxx`() {
        val str = "xxxxx xxx xxxxxx xxxxxxxxxx"
        val actual = convertMatchesToList(findAllRepeatingPatterns("x", 3, str))
        assertEquals(listOf("xxxxx", "xxx", "xxxxxx", "xxxxxxxxxx"), actual)
    }

    @Test
    fun `should match complex patterns`() {
        val str = "patternpattern pattern"
        val expected = listOf("patternpattern", "pattern")
        val actual = convertMatchesToList(findAllRepeatingPatterns("pattern", 1, str))
        assertEquals(expected, actual)
    }

    @Test
    fun `should work for big strings with complex patters`() {
        val patternUnit = "complex-pattern"
        val (test, expected) = generateBigTest(patternUnit, 100, 5)
        val actual = convertMatchesToList(findAllRepeatingPatterns(patternUnit, 5, test))
        assertEquals(expected, actual)
    }

    // howManyCharactersNeedToBeRemoved
    @Test
    fun `should return 1 for xxx`() {
        val str = "xxx"
        assertEquals(1, countIllegalCharacters("x", 3, str))
    }

    @Test
    fun `should return 2 for xxxx`() {
        val str = "xxxx"
        assertEquals(2, countIllegalCharacters("x", 3, str))
    }

    @Test
    fun `should return (len - 2) for long xxx`() {
        val str = "x".repeat(100)
        assertEquals(str.length - 2, countIllegalCharacters("x", 3, str))
    }

    @Test
    fun `should return 2 for xxx xxx`() {
        val str = "xxx xxx"
        assertEquals(2, countIllegalCharacters("x", 3, str))
    }

    @Test
    fun `should return 3 for xxx xxxx`() {
        val str = "xxx xxxx"
        assertEquals(3, countIllegalCharacters("x", 3, str))
    }

    @Test
    fun `should return 4 for xxxx xxxx`() {
        val str = "xxxx xxxx"
        assertEquals(4, countIllegalCharacters("x", 3, str))
    }

    @Test
    fun `should consider every xxx with rule (len - 2)`() {
        val patternUnit = "x"
        val (test, _, expectedCharsNum) = generateBigTest(patternUnit, 100)
        assertEquals(expectedCharsNum, countIllegalCharacters(patternUnit, 3, test))
    }

    @Test
    fun `should consider every xxx in big test`() {
        val patternUnit = "x"
        val (test, _, expectedCharsNum) = generateBigTest(patternUnit, 1000)
        assertEquals(expectedCharsNum, countIllegalCharacters(patternUnit, 3, test))
    }

    @Test
    fun `should work with complex patterns`() {
        val test = "patternpattern"
        val expected = 7
        assertEquals(expected, countIllegalCharacters("pattern", 2, test))
    }

    @Test
    fun `should work with several words with complex patterns`() {
        val test = "patternpatternOoOpatternpatternpattern"
        val expected = 21
        assertEquals(expected, countIllegalCharacters("pattern", 2, test))
    }

    @Test
    fun `if repetitionsNumber is 1 then should return number of all chars in all patterns`() {
        val test = "xyzxyzxyztestxyzxyz--xyz"
        val expected = 18
        assertEquals(expected, countIllegalCharacters("xyz", 1, test))
    }

    @Test
    fun `should return zero if target string is empty 1`() {
        val expected = 0
        assertEquals(expected, countIllegalCharacters("xyz", 1, ""))
    }

    @Test
    fun `should return zero if target string is empty 2`() {
        val expected = 0
        assertEquals(
            expected,
            countIllegalCharacters("xyzasd", 4, "")
        )
    }

    @Test
    fun `should return 0 if target string does not contain banned patterns`() {
        val expected = 0
        assertEquals(
            expected,
            countIllegalCharacters("x", 4, "no banned patterns")
        )
    }

    @Test
    fun `should return 0 if target string contains banned patterns but they repeat a legal number of times`() {
        val expected = 0
        val test = "legal xxx nothing xx and x"
        assertEquals(
            expected,
            countIllegalCharacters("x", 4, test)
        )
    }

    @Test
    fun `should throw if repetitionsNumber is 0`() {
        assertThrows(IllegalArgumentException::class.java) {
            countIllegalCharacters("xyzasd", 0, "")
        }
    }

    @Test
    fun `should throw if repetitionsNumber is less than 0`() {
        assertThrows(IllegalArgumentException::class.java) {
            countIllegalCharacters("xyzasd", -1, "")
        }
    }
}
