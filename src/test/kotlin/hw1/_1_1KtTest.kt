package hw1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class _1_1KtTest {

    @Test
    fun mutateReverse_reversesList() {
        val list: MutableList<Int> = MutableList(10) { it }
        mutateReverse(list)
        assertEquals(list, MutableList(10) { 9 - it })
    }

    @Test
    fun mutateReverse_reversesEmptyList() {
        val list: MutableList<Int> = MutableList(0) { it }
        mutateReverse(list)
        assertEquals(list, MutableList(0) { it })
    }

    @Test
    fun mutateReverse_reversesPartOfList() {
        val list: MutableList<Int> = MutableList(10) { it }
        mutateReverse(list, 3, 7)
        val expectedResult = mutableListOf<Int>(0, 1, 2, 7, 6, 5, 4, 3, 8, 9)
        assertEquals(expectedResult, list)
    }

    @Test
    fun mutateReverse_isDeterministic() {
        val list: MutableList<Int> = MutableList(100) { it }
        val listCopy: MutableList<Int> = MutableList(100) { it }
        mutateReverse(list, 10, 50)
        mutateReverse(listCopy, 10, 50)
        assertEquals(list, listCopy)
    }

    @Test
    fun mutateSwapListPartitions_throwsExceptionWhenPartitionSizesAreTooBig() {
        val list: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
        try {
            mutateSwapListPartitions(list, 10, 10)
            fail("An IllegalArgumentException should be thrown")
        } catch (e: IllegalArgumentException) {
            // do nothing
        }
    }

    @Test
    fun mutateSwapListPartitions_throwsExceptionWhenPartitionSizesAreTooSmall() {
        val list: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
        try {
            mutateSwapListPartitions(list, 1, 1)
            fail("An IllegalArgumentException should be thrown")
        } catch (e: IllegalArgumentException) {
            // do nothing
        }
    }

    @Test
    fun mutateSwapListPartitions_doesNothingIfListIsEmpty() {
        val list: MutableList<Int> = mutableListOf()
        mutateSwapListPartitions(list, 0, 0)
        assertEquals(0, list.size)
    }

    @Test
    fun mutateSwapListPartitions_swapsEqualPartitions() {
        val list1 = mutableListOf<Int>(1, 2)
        val list2 = mutableListOf<Int>(2, 1)
        mutateSwapListPartitions(list1, 1, 1)
        assertEquals(list1, list2)
    }

    @Test
    fun mutateSwapListPartitions_worksCorrectlyOnDefaultList() {
        val list = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val expectedResult = mutableListOf<Int>(4, 5, 6, 7, 8, 9, 10, 1, 2, 3)
        mutateSwapListPartitions(list, 3, 7)
        assertEquals(expectedResult, list)
    }
}