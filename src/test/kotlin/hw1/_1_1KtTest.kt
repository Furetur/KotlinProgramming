package hw1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class _1_1KtTest {

    @Test
    fun mutateReverse_reversesList1() {
        val list: MutableList<Int> = MutableList(10) { it }
        mutateReverse(list)
        assertEquals(list, MutableList(10) { 9 - it })
    }

    @Test
    fun mutateReverse_reversesList2() {
        val list: MutableList<Int> = MutableList(100) { it }
        mutateReverse(list)
        assertEquals(list, MutableList(100) { 99 - it })
    }

    @Test
    fun mutateReverse_reversesEmptyList() {
        val list: MutableList<Int> = MutableList(0) { it }
        mutateReverse(list)
        assertEquals(list, MutableList(0) { it })
    }

    @Test
    fun mutateReverse_reversesPartOfList1() {
        val list: MutableList<Int> = MutableList(10) { it }
        mutateReverse(list, 3, 7)
        val expectedResult = mutableListOf<Int>(0, 1, 2, 7, 6, 5, 4, 3, 8, 9)
        assertEquals(expectedResult, list)
    }

    @Test
    fun mutateReverse_reversesPartOfList2() {
        val list: MutableList<Int> = MutableList(20) { it }
        mutateReverse(list, 10, 15)
        val expectedResult = mutableListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 15, 14, 13, 12, 11, 10, 16, 17, 18, 19)
        assertEquals(expectedResult, list)
    }

    @Test
    fun mutateReverse_reversesPartOfList3() {
        val list: MutableList<Int> = MutableList(100) { it }
        mutateReverse(list, 50, 70)
        val expectedResult = MutableList(50) { it }
        expectedResult.addAll(MutableList(21) { 70 - it })
        expectedResult.addAll(MutableList(29) { it + 71})
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
    fun mutateSwapListPartitions_swapsEqualPartitions1() {
        val list1 = mutableListOf<Int>(1, 2)
        val list2 = mutableListOf<Int>(2, 1)
        mutateSwapListPartitions(list1, 1, 1)
        assertEquals(list1, list2)
    }

    @Test
    fun mutateSwapListPartitions_swapsEqualPartitions2() {
        val list1 = MutableList(100) { if (it < 50) 0 else 1 }
        val list2 = MutableList(100) { if (it >= 50) 0 else 1 }
        mutateSwapListPartitions(list1, 50, 50)
        assertEquals(list1, list2)
    }

    @Test
    fun mutateSwapListPartitions_worksCorrectlyOnDefaultList() {
        val list = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val expectedResult = mutableListOf<Int>(4, 5, 6, 7, 8, 9, 10, 1, 2, 3)
        mutateSwapListPartitions(list, 3, 7)
        assertEquals(expectedResult, list)
    }

    @Test
    fun mutateSwapListPartitions_worksCorrectlyOnBigLists() {
        val part1 = MutableList(300) {it}
        val part2 = MutableList(1000) {it * 2}
        val list = mutableListOf<Int>()
        list.addAll(part1)
        list.addAll(part2)
        mutateSwapListPartitions(list, 300, 1000)
        val expectedList = mutableListOf<Int>()
        expectedList.addAll(part2)
        expectedList.addAll(part1)
        assertEquals(expectedList, list)
    }
}