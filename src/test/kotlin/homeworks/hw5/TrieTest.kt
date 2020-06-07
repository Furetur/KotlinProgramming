package homeworks.hw5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertFalse

import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException

internal class TrieTest {

    private val trie = Trie()

    @BeforeEach
    fun emptyTrie() {
        trie.clear()
    }

    @Test
    fun `size of empty trie should be 0`() {
        assertEquals(0, trie.size)
    }

    @Test
    fun `size should be equal to number of stored elements`() {
        trie.add("a")
        trie.add("abba")
        trie.add("zero")
        assertEquals(3, trie.size)
    }

    @Test
    fun `size should not change if the same element is added twice`() {
        val trie = Trie()
        trie.add("a")
        trie.add("a")
        trie.add("zero")
        trie.add("hero")
        assertEquals(3, trie.size)
    }

    @Test
    fun `size should not ignore empty strings`() {
        val trie = Trie()
        trie.add("")
        assertEquals(1, trie.size)
    }

    @Test
    fun `add should throw if element with a newline character is added`() {
        assertThrows(IllegalArgumentException::class.java) {
            trie.add("eleme\nnt")
        }
    }

    @Test
    fun `add should return true if element was not present`() {
        assert(trie.add("aa"))
    }

    @Test
    fun `add should return if new element is added into a big trie`() {
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assert(trie.add("does not exist"))
    }

    @Test
    fun `add should return false if element is already in the trie`() {
        trie.add("121")
        assertFalse(trie.add("121"))
    }

    @Test
    fun `add should return false if element is already in a big trie`() {
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assertFalse(trie.add("154"))
    }

    @Test
    fun `contains should return true if element is in the trie`() {
        trie.add("1001")
        assert(trie.contains("1001"))
    }

    @Test
    fun `contains should return false if element is not in the trie`() {
        trie.add("1001")
        assertFalse(trie.contains("10010"))
    }

    @Test
    fun `contains should return true if element is present in a big trie`() {
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assert(trie.contains("153"))
    }

    @Test
    fun `contains should return false if element is not in the big trie`() {
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assertFalse(trie.contains("is not there"))
    }

    @Test
    fun `remove should always return false if called on an empty trie`() {
        assertFalse(trie.remove("ada"))
    }

    @Test
    fun `return should return false if element was not removed`() {
        trie.add("101")
        assertFalse(trie.remove("ada"))
    }

    @Test
    fun `remove should return true if element was removed`() {
        trie.add("151")
        assert(trie.remove("151"))
    }

    @Test
    fun `remove should remove an element`() {
        trie.add("element")
        trie.remove("element")
        assertFalse(trie.contains("element"))
    }

    @Test
    fun `empty should remove turn size to 0`() {
        for (i in 0..1000) {
            trie.add("a$i")
        }
        trie.clear()
        assertEquals(0, trie.size)
    }

    // this tests .iterator()
    @Test
    fun `after emptying toSet should return an empty set`() {
        for (i in 0..1000) {
            trie.add("a$i")
        }
        trie.clear()
        assertEquals(setOf<String>(), trie.toSet())
    }

    @Test
    fun `iterator should iterate over all values`() {
        val expected = mutableSetOf<String>()
        for (i in 0..100) {
            val value = i.toString()
            expected.add(value)
            trie.add(value)
        }
        val actual = mutableSetOf<String>()
        for (value in trie) {
            actual.add(value)
        }
        assertEquals(expected, actual)
    }

    // howManyStartWithPrefix tests

    @Test
    fun `howManyStartWithPrefix should work for single letters`() {
        trie.add("aab")
        trie.add("aac")
        trie.add("abc")
        trie.add("bbc")
        assertEquals(3, trie.howManyStartWithPrefix("a"))
    }

    @Test
    fun `howManyStartWithPrefix should work for single letters in big trie`() {
        for (i in 0..1999) {
            trie.add("a$i")
        }
        for (i in 0..1000) {
            trie.add("b$i")
        }
        assertEquals(2000, trie.howManyStartWithPrefix("a"))
    }

    @Test
    fun `howManyStartWithPrefix should return size of trie for empty prefix`() {
        trie.add("aab")
        trie.add("aac")
        trie.add("abc")
        trie.add("bbc")
        assertEquals(4, trie.howManyStartWithPrefix(""))
    }

    @Test
    fun `howManyStartWithPrefix should return size of trie for empty prefix big`() {
        for (i in 0..1000) {
            trie.add("a$i")
        }
        for (i in 0..1000) {
            trie.add("b$i")
        }
        assertEquals(2002, trie.howManyStartWithPrefix(""))
    }

    @Test
    fun `howManyStartWithPrefix should work with long prefixes of single letter`() {
        for (i in 1..1000) {
            trie.add("a".repeat(i))
        }
        val prefix = "a".repeat(500)
        assertEquals(501, trie.howManyStartWithPrefix(prefix))
    }

    @Test
    fun `howManyStartWithPrefix should work with complex prefixes in big trie`() {
        val prefix1 = "abc".repeat(10)
        val prefix2 = "xyz".repeat(10)
        val prefix3 = "asd".repeat(10)
        for (i in 1..1000) {
            trie.add("$prefix1 $i")
        }
        for (i in 1..500) {
            trie.add("$prefix2 $i")
        }
        for (i in 1..700) {
            trie.add("$prefix3 $i")
        }
        assertEquals(500, trie.howManyStartWithPrefix(prefix2))
    }

    private val prefixUnit = "prefixUnit"

    private fun generateDeepTrie() {
        var curPrefix = ""
        for (i in 1..100) {
            curPrefix += prefixUnit
            for (j in 1..50) {
                trie.add("$curPrefix $j")
            }
        }
    }

    @Test
    fun `howManyStartWithPrefix should work with long complex prefixes 1`() {
        generateDeepTrie()
        val prefix = prefixUnit
        assertEquals(5000, trie.howManyStartWithPrefix(prefix))
    }

    @Test
    fun `howManyStartWithPrefix should work with long complex prefixes 2`() {
        generateDeepTrie()
        val prefix = prefixUnit.repeat(51)
        assertEquals(2500, trie.howManyStartWithPrefix(prefix))
    }

    // serialization and deserialization tests

    @Test
    fun `output stream should not be empty after serialization`() {
        for (i in 1..10) {
            trie.add(i.toString())
        }
        val output = ByteArrayOutputStream()
        trie.writeObject(output)
        assert(output.size() > 0)
    }

    @Test
    fun `deserialize should create a trie from serialized data`() {
        for (i in 1..10) {
            trie.add(i.toString())
        }
        val output = ByteArrayOutputStream()
        trie.writeObject(output)
        val input = ByteArrayInputStream(output.toByteArray())
        val newTrie = Trie()
        newTrie.readObject(input)
        assertEquals(trie.toSet(), newTrie.toSet())
    }
}
