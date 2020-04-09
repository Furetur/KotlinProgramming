package homeworks.hw2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class _2_1KtTest {

    fun sequenceOfMatchResultsToSetOfStrings(sequence: Sequence<MatchResult>): Set<String> {
        return sequence.toSet().map { it.value }.toSet()
    }

    // matchAllConsecutiveX
    @Test
    fun `should match 1 xxx`() {
        val str = "xxx"
        val actual = sequenceOfMatchResultsToSetOfStrings(matchAllConsecutiveX(str))
        assertEquals(setOf("xxx"), actual)
    }

    @Test
    fun `should match xxx and xxxx`() {
        val str = "xxx and xxxx"
        val actual = sequenceOfMatchResultsToSetOfStrings(matchAllConsecutiveX(str))
        assertEquals(setOf("xxx", "xxxx"), actual)
    }

    @Test
    fun `should match long xxxxxxxx`() {
        val longXxx = "x".repeat(100)
        val testString = "test $longXxx end"
        val actual = sequenceOfMatchResultsToSetOfStrings(matchAllConsecutiveX(testString))
        assertEquals(setOf(longXxx), actual)
    }

    @Test
    fun `should match all xxx`() {
        val str = "xxxxx xxx xxxxxx xxxxxxxxxx"
        val actual = sequenceOfMatchResultsToSetOfStrings(matchAllConsecutiveX(str))
        assertEquals(setOf("xxxxx", "xxx", "xxxxxx", "xxxxxxxxxx"), actual)
    }

    // howManyCharactersNeedToBeRemoved
    @Test
    fun `should return 1 for xxx`() {
        val str = "xxx"
        assertEquals(1, howManyCharactersNeedToBeRemoved(str))
    }

    @Test
    fun `should return 2 for xxxx`() {
        val str = "xxxx"
        assertEquals(2, howManyCharactersNeedToBeRemoved(str))
    }

    @Test
    fun `should return (len - 2) for long xxx`() {
        val str = "x".repeat(100)
        assertEquals(str.length - 2, howManyCharactersNeedToBeRemoved(str))
    }

    @Test
    fun `should return 2 for xxx xxx`() {
        val str = "xxx xxx"
        assertEquals(2, howManyCharactersNeedToBeRemoved(str))
    }

    @Test
    fun `should return 3 for xxx xxxx`() {
        val str = "xxx xxxx"
        assertEquals(3, howManyCharactersNeedToBeRemoved(str))
    }

    @Test
    fun `should return 4 for xxxx xxxx`() {
        val str = "xxxx xxxx"
        assertEquals(4, howManyCharactersNeedToBeRemoved(str))
    }

    @Test
    fun `should consider every xxx with rule (len - 2)`() {
        var expected = 0
        var str = ""
        var curX = "x"
        for (i in 0..100) {
            str += " $curX"
            if (curX.length >= 3) {
                expected += curX.length - 2
            }
            curX += "x"
        }

        assertEquals(expected, howManyCharactersNeedToBeRemoved(str))
    }
}