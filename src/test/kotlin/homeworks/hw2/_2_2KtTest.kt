package homeworks.hw2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class _2_2KtTest {
    @Test
    fun `should not edit lists that already do not contain duplicate elements`() {
        val testList = listOf(1, 2, 3, 4, 5, 6)
        assertEquals(testList, distinctRight(testList))
    }

    @Test
    fun `should remove all elements except one from list that contains 1 repeating element`() {
        val testList = List(100) { 0 }
        assertEquals(listOf(0), distinctRight(testList))
    }

    @Test
    fun `should work for empty lists`() {
        val testList = List(0) { it }
        assertEquals(testList, distinctRight(testList))
    }

    @Test
    fun `should not edit initial list`() {
        val testList = List(100) { it % 3 }
        val copyList = List(100) { it % 3 }
        distinctRight(testList)
        assertEquals(copyList, testList)
    }

    @Test
    fun `should return right part of mirrored list`() {
        val leftList = MutableList(100) { it }
        val rightList = leftList.reversed()

        val mirroredList = leftList.toMutableList()
        mirroredList.addAll(rightList)

        assertEquals(rightList, distinctRight(mirroredList))
    }

    @Test
    fun `should only keep right occurrences 1`() {
        val testList = List(100) { it % 3 }
        val expected = listOf(1, 2, 0)

        assertEquals(expected, distinctRight(testList))
    }

    @Test
    fun `should only keep right occurrences 2`() {
        val testList = List(1000) { it % 7 }
        val expected = listOf(6, 0, 1, 2, 3, 4, 5)

        assertEquals(expected, distinctRight(testList))
    }
}