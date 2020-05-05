package homeworks.hw6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal abstract class SortTest {

    abstract fun <E : Comparable<E>> sort(list: MutableList<E>)

    private fun generateListWithShuffledElements(chunkSize: Int, chunksNum: Int): MutableList<Int> {
        var currentStartValue = 0
        val list = mutableListOf<Int>()

        for (i in 1..chunksNum) {
            val chunk = (currentStartValue..currentStartValue + chunkSize).toMutableList()
            if (i % 2 == 0) {
                chunk.reverse()
            }
            list.addAll(chunk)
            currentStartValue += chunkSize - 1
        }
        return list
    }

    @Test
    fun `should not change empty list`() {
        val emptyList = mutableListOf<Int>()
        sort(emptyList)
        assertEquals(mutableListOf<Int>(), emptyList)
    }

    @Test
    fun `should work for already sorted lists`() {
        val list = MutableList<Int>(100) { it }
        val expected = list.sorted()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort lists of size 2`() {
        val list = mutableListOf<Int>(100, 1)
        val expected = list.sorted()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort string lists of size 2`() {
        val list = mutableListOf<String>("second string", "first string")
        val expected = list.sorted()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort big reversed lists`() {
        val list = MutableList(1000) { it }
        val expected = list.sorted()
        list.reverse()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort extra big reversed lists`() {
        val list = MutableList(10000) { it }
        val expected = list.sorted()
        list.reverse()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort lists with repeating elements`() {
        val list = MutableList(10000) { it % 3 }
        val expected = list.sorted()
        list.reverse()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort big shuffled lists`() {
        val list = generateListWithShuffledElements(5, 100)
        val expected = list.sorted()
        list.reverse()
        sort(list)
        assertEquals(expected, list)
    }

    @Test
    fun `should sort big shuffled lists 2`() {
        val list = generateListWithShuffledElements(10, 300)
        val expected = list.sorted()
        list.reverse()
        sort(list)
        assertEquals(expected, list)
    }
}
