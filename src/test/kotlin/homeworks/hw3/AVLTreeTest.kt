package homeworks.hw3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach

internal class AVLTreeTest {

    private val stringComparator = Comparator<String> { str1, str2 -> str1.compareTo(str2) }

    private val intComparator = Comparator<Int> { num1, num2 -> num1 - num2 }

    private val emptyTree = AVLTree<String, Int>(stringComparator)

    private val populatedTree = AVLTree<String, Int>(stringComparator)

    private fun generateBigTree(size: Int): Pair<AVLTree<String, Int>, Set<Pair<String, Int>>> {
        val tree = AVLTree<String, Int>(stringComparator)
        val expectedEntries = mutableSetOf<Pair<String, Int>>()
        for (i in 1..size) {
            val curKey = "key - $i"
            tree[curKey] = i
            expectedEntries.add(Pair(curKey, i))
        }
        return Pair(tree, expectedEntries)
    }

    @BeforeEach
    fun initTrees() {
        emptyTree.clear()
        populatedTree.clear()
        populatedTree["a"] = 1
        populatedTree["b"] = 2
        populatedTree["c"] = 4
        populatedTree["d"] = 3
        populatedTree["e"] = 5
    }

    @Test
    fun `entries should be empty if tree is empty`() {
        assertEquals(0, emptyTree.entries.size)
    }

    @Test
    fun `entries should return all entries of the populated tree`() {
        val actualEntries = populatedTree.entries.map { Pair(it.key, it.value) }.toSet()
        val expectedEntries = setOf(Pair("a", 1), Pair("b", 2), Pair("c", 4), Pair("d", 3), Pair("e", 5))
        assertEquals(expectedEntries, actualEntries)
    }

    @Test
    fun `keys should be an empty set for empty tree`() {
        assertEquals(0, emptyTree.keys.size)
    }

    @Test
    fun `keys should return all put keys`() {
        val expectedKeys = setOf("a", "b", "c", "d", "e")
        assertEquals(expectedKeys, populatedTree.keys)
    }

    @Test
    fun `size of empty tree should be 0`() {
        assertEquals(0, emptyTree.size)
    }

    @Test
    fun `size of populated tree should be equal to number of put items`() {
        assertEquals(5, populatedTree.size)
    }

    @Test
    fun `values should be empty if tree is empty`() {
        assertEquals(0, emptyTree.values.size)
    }

    @Test
    fun `values should contain all put values`() {
        val actualValues = populatedTree.values
        val expectedValues = setOf(1, 2, 3, 4, 5)
        assert(expectedValues.containsAll(actualValues) && actualValues.containsAll(expectedValues))
    }

    @Test
    fun `contains should return true for keys that are in tree`() {
        assert(populatedTree.contains("d"))
    }

    @Test
    fun `contains should return false for keys that are not in tree`() {
        assertFalse(populatedTree.contains("n"))
    }

    @Test
    fun `contains value should return true for values that are in tree`() {
        assert(populatedTree.containsValue(1))
    }

    @Test
    fun `contains value should return false for values that are not in tree`() {
        assertFalse(populatedTree.containsValue(10))
    }

    @Test
    fun `get should return correct value for key`() {
        assertEquals(4, populatedTree["c"])
    }

    @Test
    fun `get should return null if key is not in tree`() {
        assertEquals(null, populatedTree["ab"])
    }

    @Test
    fun `get should always return null if tree is empty`() {
        assertEquals(null, emptyTree["a"])
    }

    @Test
    fun `isEmpty should return true if tree is empty`() {
        assert(emptyTree.isEmpty())
    }

    @Test
    fun `isEmpty should return false if tree is not empty`() {
        assertFalse(populatedTree.isEmpty())
    }

    @Test
    fun `values that are put into empty tree should be gettable`() {
        emptyTree["a"] = 1
        assertEquals(1, emptyTree["a"])
    }

    @Test
    fun `values that are put into populated tree should be gettable`() {
        populatedTree["abb"] = 123
        assertEquals(123, populatedTree["abb"])
    }

    @Test
    fun `put should increate tree size by 1`() {
        val oldSize = populatedTree.size
        populatedTree["132"] = 132
        assertEquals(oldSize + 1, populatedTree.size)
    }

    @Test
    fun `put should update existing entries if key is already in tree`() {
        emptyTree["a"] = 1
        emptyTree["a"] = 2
        assertEquals(2, emptyTree["a"])
    }

    @Test
    fun `remove should remove existing keys`() {
        populatedTree.remove("b")
        assertEquals(null, populatedTree["b"])
    }

    @Test
    fun `remove should do nothing if requested to remove an unexisting key`() {
        populatedTree.remove("aab")
        assertEquals(null, populatedTree["aab"])
    }

    @Test
    fun `remove should do nothing on empty trees`() {
        emptyTree.remove("1")
        assertEquals(null, emptyTree["1"])
    }

    @Test
    fun `remove should decrease size by 1 in populated tree`() {
        val oldSize = populatedTree.size
        populatedTree.remove("a")
        assertEquals(oldSize - 1, populatedTree.size)
    }

    @Test
    fun `remove should not change size if key is not present`() {
        populatedTree.remove("zyx")
        assertEquals(populatedTree.size, populatedTree.size)
    }

    @Test
    fun `remove should not change size of empty tree`() {
        emptyTree.remove("a")
        assertEquals(0, emptyTree.size)
    }

    // big tests

    @Test
    fun `should store all given key value pairs`() {
        val (tree, expectedEntries) = generateBigTree(100)
        val actualEntriesAsPairs = tree.entries.map { Pair(it.key, it.value) }.toSet()
        assertEquals(expectedEntries, actualEntriesAsPairs)
    }

    @Test
    fun `containsKey should return false for keys that are not present in big trees`() {
        val (tree, _) = generateBigTree(100)
        assertFalse(tree.containsKey("10000"))
    }

    @Test
    fun `containsKey should return true for keys that are present in big trees`() {
        val (tree, _) = generateBigTree(100)
        assert(tree.containsKey("key - 50"))
    }

    @Test
    fun `size should be equal to the number of entries`() {
        val (tree, _) = generateBigTree(1000)
        assertEquals(1000, tree.size)
    }

    @Test
    fun `get should return value in big tree`() {
        val (tree, _) = generateBigTree(1000)
        assertEquals(150, tree["key - 150"])
    }

    @Test
    fun `remove should remove entry from big tree`() {
        val (tree, _) = generateBigTree(1000)
        tree.remove("key - 150")
        assertFalse(tree.containsKey("key - 150"))
    }

    @Test
    fun `put should update entry in big tree`() {
        val (tree, _) = generateBigTree(1000)
        tree["key - 150"] = -1
        assertEquals(-1, tree["key - 150"])
    }

    @Test
    fun `put should put new entries in big tree`() {
        val (tree, _) = generateBigTree(1000)
        tree["new key"] = -1
        assertEquals(-1, tree["new key"])
    }

    @Test
    fun `containsValue should return false if value is not present in big tree`() {
        val (tree, _) = generateBigTree(1000)
        assertFalse(tree.containsValue(-5))
    }

    @Test
    fun `containsValue should return true if value is in big tree`() {
        val (tree, _) = generateBigTree(1000)
        assert(tree.containsValue(777))
    }
}
