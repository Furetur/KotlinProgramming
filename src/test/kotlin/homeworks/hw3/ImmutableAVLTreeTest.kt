package homeworks.hw3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse

val emptyTree = ImmutableAVLTree<String, Int>(Comparator<String> { str1, str2 ->
    str1.compareTo(str2)
})
val populatedTree = emptyTree.put("a", 1).put("b", 2).put("c", 4).put("d", 3).put("e", 5)

internal class ImmutableAVLTreeTest {

    @Test
    fun getEntries_shouldReturnAnEmptySetForEmptyTree() {
        assertEquals(0, emptyTree.entries.size)
    }

    @Test
    fun getEntries_shouldReturnAllEntriesForPopulatedTree() {
        val actualEntries = populatedTree.entries
        val expectedEntriesStrings = setOf(
            ImmutableAVLTree.Node("a", 1),
            ImmutableAVLTree.Node("b", 2),
            ImmutableAVLTree.Node("c", 4),
            ImmutableAVLTree.Node("d", 3),
            ImmutableAVLTree.Node("e", 5)
        ).map { it.toString() }
        val actualEntriesStrings = actualEntries.map { it.toString() }
        assert(
            actualEntriesStrings.containsAll(expectedEntriesStrings) && expectedEntriesStrings.containsAll(
                actualEntriesStrings
            )
        )
    }

    @Test
    fun getKeys_shouldReturnEmptySetForEmptyTree() {
        assertEquals(0, emptyTree.keys.size)
    }

    @Test
    fun getKeys_shouldReturnAllKeysOfPopulatedTree() {
        val expectedKeys = setOf("a", "b", "c", "d", "e")
        assertEquals(expectedKeys, populatedTree.keys)
    }

    @Test
    fun getSize_shouldReturnCorrectZeroForEmptyTree() {
        assertEquals(0, emptyTree.size)
    }

    @Test
    fun getSize_shouldReturnCorrectSizeForPopulatedTree() {
        assertEquals(5, populatedTree.size)
    }

    @Test
    fun getValues_shouldReturnEmptySetForEmptyTree() {
        assertEquals(0, emptyTree.values.size)
    }

    @Test
    fun getValues_shouldReturnCorrectSetForPopulatedTree() {
        val actualValues = populatedTree.values
        val expectedValues = setOf(1, 2, 3, 4, 5)
        assert(expectedValues.containsAll(actualValues) && actualValues.containsAll(expectedValues))
    }

    @Test
    fun containsKey_shouldReturnTrueForKeysInTree() {
        assert(populatedTree.contains("d"))
    }

    @Test
    fun containsKey_shouldReturnFalseForKeysNotInTree() {
        assertFalse(populatedTree.contains("n"))
    }

    @Test
    fun containsValue_shouldReturnTrueForValuesInTree() {
        assert(populatedTree.containsValue(1))
    }

    @Test
    fun containsValue_shouldReturnFalseForValuesNotInTree() {
        assertFalse(populatedTree.containsValue(10))
    }

    @Test
    fun get_shouldReturnCorrectValuesForKeysInTree() {
        assertEquals(4, populatedTree["c"])
    }

    @Test
    fun get_shouldReturnNullForKeysNotInTree() {
        assertEquals(null, populatedTree["ab"])
    }

    @Test
    fun get_shouldReturnNullForEmptyTree() {
        assertEquals(null, emptyTree["a"])
    }

    @Test
    fun isEmpty_shouldReturnTrueForEmptyTree() {
        assert(emptyTree.isEmpty())
    }

    @Test
    fun isEmpty_shouldReturnFalseForPopulatedTree() {
        assertFalse(populatedTree.isEmpty())
    }

    @Test
    fun put_shouldSaveNewGettableValueInEmptyTree() {
        val newTree = emptyTree.put("a", 1)
        assertEquals(1, newTree["a"])
    }

    @Test
    fun put_shouldSaveNewGettableValueInPopulatedTree() {
        val newTree = populatedTree.put("abb", 123)
        assertEquals(123, newTree["abb"])
    }

    @Test
    fun put_shouldIncreaseTreesSizeByOne() {
        val newTree = populatedTree.put("132", 132)
        assertEquals(populatedTree.size + 1, newTree.size)
    }

    @Test
    fun put_shouldUpdateExistingEntries() {
        val newTree = emptyTree.put("a", 1).put("a", 2)
        assertEquals(2, newTree["a"])
    }

    @Test
    fun remove_shouldRemoveExistingKeys() {
        val newTree = populatedTree.remove("b")
        assertEquals(null, newTree["b"])
    }

    @Test
    fun remove_shouldNotRemoveKeysThatDoNotExist() {
        val newTree = populatedTree.remove("aab")
        assertEquals(null, newTree["aab"])
    }

    @Test
    fun remove_shouldNotRemoveAnythingForEmptyTrees() {
        val newTree = emptyTree.remove("1")
        assertEquals(null, newTree["1"])
    }

    @Test
    fun remove_shouldDecreaseSizeByOneInPopulatedTree() {
        val newTree = populatedTree.remove("a")
        assertEquals(populatedTree.size - 1, newTree.size)
    }

    @Test
    fun remove_shouldNotChangeSizeOfEmptyTree() {
        val newTree = emptyTree.remove("a")
        assertEquals(0, newTree.size)
    }

    @Test
    fun `should store all given key value pairs`() {
        val intComparator = Comparator<Int> { num1, num2 -> num1 - num2 }
        var newTree = ImmutableAVLTree<Int, Int>(intComparator)
        val expectedEntries = mutableSetOf<Pair<Int, Int>>()
        for (i in 0..100) {
            newTree = newTree.put(i, i)
            expectedEntries.add(Pair(i, i))
        }
        val actualEntriesAsPairs = newTree.entries.map { Pair(it.key, it.value) }.toSet()
        assertEquals(expectedEntries.toSet(), actualEntriesAsPairs)
    }

    @Test
    fun containsKey_shouldReturnFalseForValuesNotInBigTree() {
        val intComparator = Comparator<Int> { num1, num2 -> num1 - num2 }
        var newTree = ImmutableAVLTree<Int, Int>(intComparator)
        for (i in 0..1000) {
            newTree = newTree.put(i, i)
        }
        assertFalse(newTree.containsKey(10000))
    }

    @Test
    fun containsValue_shouldReturnFalseForValuesNotInBigTree() {
        val intComparator = Comparator<Int> { num1, num2 -> num1 - num2 }
        var newTree = ImmutableAVLTree<Int, String>(intComparator)
        for (i in 0..1000) {
            newTree = newTree.put(i, "value=$i")
        }
        assertFalse(newTree.containsValue("value=100000"))
    }

    @Test
    fun containsValue_shouldReturnTrueForValuesInBigTree() {
        val intComparator = Comparator<Int> { num1, num2 -> num1 - num2 }
        var newTree = ImmutableAVLTree<Int, String>(intComparator)
        for (i in 0..1000) {
            newTree = newTree.put(i, "value=$i")
        }
        assert(newTree.containsValue("value=777"))
    }
}
