package homeworks.hw4

import homeworks.hw4.TextCommandRunner.Companion.defaultHashFunction
import homeworks.hw4.TextCommandRunner.Companion.trivialHashFunction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class HashTableTest {
    var hashTable = HashTable<String, String>(defaultHashFunction)

    @BeforeEach
    fun init() {
        hashTable = HashTable(defaultHashFunction)
    }

    @Test
    fun `should have initial size of 0`() {
        assertEquals(0, hashTable.size)
    }

    @Test
    fun `should be initially empty`() {
        assert(hashTable.isEmpty())
    }

    @Test
    fun `should not be empty after putting keys`() {
        hashTable["k"] = "v"
        assertFalse(hashTable.isEmpty())
    }

    @Test
    fun `put should increment size if new element is added`() {
        hashTable["key"] = "value"
        assertEquals(1, hashTable.size)
    }

    @Test
    fun `put should not increment size if one key is put multiple times`() {
        hashTable["key"] = "value"
        hashTable["key"] = "newvalue"
        assertEquals(1, hashTable.size)
    }

    @Test
    fun `size should be equal to number of put keys`() {
        for (i in 0..99) {
            hashTable["key$i"] = "val$i"
        }
        assertEquals(100, hashTable.size)
    }

    @Test
    fun `remove should decrement size`() {
        hashTable["key"] = "value"
        hashTable.remove("key")
        assertEquals(0, hashTable.size)
    }

    @Test
    fun `remove should not decrement size if no key is removed`() {
        hashTable["key"] = "value"
        hashTable.remove("non-existing key")
        assertEquals(1, hashTable.size)
    }

    @Test
    fun `put should return previous value`() {
        hashTable["key"] = "value"
        val prevValue = hashTable.put("key", "newValue")
        assertEquals("value", prevValue)
    }

    @Test
    fun `put should return null if there was no previous value`() {
        hashTable["key"] = "value"
        val prevValue = hashTable.put("key1", "newValue")
        assertEquals(null, prevValue)
    }

    @Test
    fun `get should return put values`() {
        hashTable["key"] = "value"
        assertEquals("value", hashTable["key"])
    }

    @Test
    fun `initial entries should be empty`() {
        assertEquals(0, hashTable.entries.size)
    }

    @Test
    fun `all put values should be entries`() {
        val expectedPairs = mutableSetOf<Pair<String, String>>()
        for (i in 0..100) {
            hashTable["key$i"] = "val$i"
            expectedPairs.add(Pair("key$i", "val$i"))
        }
        val actualPairs = hashTable.entries.map { entry -> Pair(entry.key, entry.value) }.toMutableSet()
        assertEquals(expectedPairs, actualPairs)
    }

    @Test
    fun `all put values should be gettable`() {
        val expectedValues = mutableListOf<String>()
        for (i in 0..100) {
            hashTable["key$i"] = "val$i"
            expectedValues.add("val$i")
        }
        val actualValues = mutableListOf<String>()
        for (i in 0..100) {
            actualValues.add(hashTable["key$i"] ?: "null")
        }
        assertEquals(expectedValues, actualValues)
    }

    @Test
    fun `keys should return all put keys`() {
        val expectedKeys = mutableSetOf<String>()
        for (i in 0..100) {
            hashTable["key$i"] = "val$i"
            expectedKeys.add("key$i")
        }
        assertEquals(expectedKeys, hashTable.keys)
    }

    @Test
    fun `containsKey should return true if key was put`() {
        hashTable["key"] = "val"
        assert(hashTable.containsKey("key"))
    }

    @Test
    fun `containsKey should return false if key was not put`() {
        hashTable["key"] = "val"
        assertFalse(hashTable.containsKey("key1"))
    }

    @Test
    fun `containsValue should return true if value was put`() {
        hashTable["key"] = "val"
        assert(hashTable.containsValue("val"))
    }

    @Test
    fun `containsValue should return false if value was not put`() {
        hashTable["key"] = "val"
        assertFalse(hashTable.containsValue("val1"))
    }

    @Test
    fun `clear should empty the table`() {
        hashTable["key"] = "val"
        hashTable.clear()
        assert(hashTable.isEmpty())
    }

    @Test
    fun `remove should return removed value`() {
        hashTable["key"] = "val"
        val prevValue = hashTable.remove("key")
        assertEquals("val", prevValue)
    }

    @Test
    fun `remove should return null if no value is removed`() {
        hashTable["key"] = "val"
        val prevValue = hashTable.remove("key1")
        assertEquals(null, prevValue)
    }

    @Test
    fun `after hash function change all put values should be gettable`() {
        val expectedValues = mutableListOf<String>()
        for (i in 0..100) {
            hashTable["key$i"] = "val$i"
            expectedValues.add("val$i")
        }
        hashTable.hashFunction = trivialHashFunction
        val actualValues = mutableListOf<String>()
        for (i in 0..100) {
            actualValues.add(hashTable["key$i"] ?: "null")
        }
        assertEquals(expectedValues, actualValues)
    }

    @Test
    fun `changing a hash function should reorganize the table`() {
        for (i in 0..1000) {
            hashTable["key$i"] = "val$i"
        }
        val oldLoadFactor = hashTable.loadFactor
        hashTable.hashFunction = trivialHashFunction
        assertEquals(oldLoadFactor, hashTable.loadFactor)
    }

    @Test
    fun `in a populated hash table load factor should be greater than 0`() {
        hashTable["val"] = "key"
        assert(hashTable.loadFactor > 0)
    }

    @Test
    fun `in an empty hash table load factor should be 0`() {
        assertEquals(0f, hashTable.loadFactor)
    }

    @Test
    fun `hash table expands when too many elements are put`() {
        val initialBucketsNumber = 5
        val hashTable = HashTable<String, String>(defaultHashFunction, initialBucketsNumber)
        repopulateHashTable(hashTable, 1000)
        val finalBucketsNumber = ((1 / hashTable.loadFactor) * 1000).toInt()
        assertNotEquals(initialBucketsNumber, finalBucketsNumber)
    }
}
