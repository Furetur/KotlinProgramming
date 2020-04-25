package homeworks.hw1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class TaskTest {
    @Test
    fun mutateSwapListPartitions_throwsExceptionWhenPartitionSizesAreTooBig() {
        val list: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
        assertThrows(IllegalArgumentException::class.java) {
            mutateSwapListPartitions(list, 10, 10)
        }
    }

    @Test
    fun mutateSwapListPartitions_throwsExceptionWhenPartitionSizesAreTooSmall() {
        val list: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
        assertThrows(IllegalArgumentException::class.java) {
            mutateSwapListPartitions(list, 1, 1)
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