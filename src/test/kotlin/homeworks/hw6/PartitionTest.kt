package homeworks.hw6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class PartitionTest {

    private fun <E : Comparable<E>> verifyLeftPartition(pivotIndex: Int, list: List<E>): Boolean {
        val pivotValue = list[pivotIndex]
        return list.subList(0, pivotIndex).all { it < pivotValue }
    }

    private fun <E : Comparable<E>> verifyRightPartition(pivotIndex: Int, list: List<E>): Boolean {
        return if (pivotIndex == list.lastIndex) {
            true
        } else {
            val pivotValue = list[pivotIndex]
            list.subList(pivotIndex + 1, list.lastIndex).all { it >= pivotValue }
        }
    }

    private fun <E : Comparable<E>> verifyPartition(pivotIndex: Int, list: List<E>): Boolean {
        return verifyLeftPartition(pivotIndex, list) && verifyRightPartition(pivotIndex, list)
    }

    @Test
    fun `should throw if given empty list`() {
        assertThrows(IllegalArgumentException::class.java) {
            partition(mutableListOf<Int>())
        }
    }

    @Test
    fun `should not change list of size 1`() {
        val list = mutableListOf<Int>(1)
        partition(list)
        assertEquals(mutableListOf<Int>(1), list)
    }

    @Test
    fun `should return the same pivot index for list of size 1`() {
        val list = mutableListOf<Int>(1)
        val newPivotIndex = partition(list)
        assertEquals(0, newPivotIndex)
    }

    @Test
    fun `should work for sorted lists`() {
        val list = MutableList(100) { it }
        val pivotIndex = partition(list)
        assert(verifyPartition(pivotIndex, list))
    }

    @Test
    fun `should return index that points to the same value`() {
        val list = MutableList(100) { it }
        val pivotValue = list.last()
        val newPivotIndex = partition(list)
        assertEquals(pivotValue, list[newPivotIndex])
    }

    @Test
    fun `should return index that points to the same value for partitioned lists`() {
        val firstPart = MutableList(100) { it }
        firstPart.reverse()
        val pivot = 1000
        val secondPart = (2000..3000).toMutableList()
        secondPart.reverse()
        val list = mutableListOf<Int>()
        list.addAll(firstPart)
        list.addAll(secondPart)
        list.add(pivot)
        list.toMutableList()
        val newIndex = partition(list)
        assertEquals(pivot, list[newIndex])
    }

    @Test
    fun `should return index that points to the same value after partitioning`() {
        val list = MutableList(1000) { 1000 - it }
        val pivot = list.last()
        val newPivotIndex = partition(list)
        assertEquals(pivot, list[newPivotIndex])
    }

    @Test
    fun `should partition lists without repeating values`() {
        val list = MutableList(1000) { 100 - it }
        val newPivotIndex = partition(list)
        assert(verifyPartition(newPivotIndex, list))
    }

    @Test
    fun `should return index that points to the same value after partitioning of lists with repeating values`() {
        val list = MutableList(1000) { it % 7 }
        val pivot = list.last()
        val newPivotIndex = partition(list)
        assertEquals(pivot, list[newPivotIndex])
    }

    @Test
    fun `should partition lists with repeating values`() {
        val list = MutableList(100) { it % 7 }
        val newPivotIndex = partition(list)
        assert(verifyPartition(newPivotIndex, list))
    }
}
