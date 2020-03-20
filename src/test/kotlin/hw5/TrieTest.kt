package hw5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail
import java.lang.IllegalArgumentException

internal class TrieTest {

    @Test
    fun getSize_shouldReturnZeroFromEmptyTrie() {
        val trie = Trie()
        assertEquals(0, trie.size)
    }

    @Test
    fun getSize_shouldReturnNumberOfStoredElements1() {
        val trie = Trie()
        trie.add("a")
        trie.add("abba")
        trie.add("zero")
        assertEquals(3, trie.size)
    }

    @Test
    fun getSize_shouldReturnNumberOfStoredElements2() {
        val trie = Trie()
        trie.add("a")
        trie.add("abba")
        trie.add("zero")
        trie.add("hero")
        assertEquals(4, trie.size)
    }

    @Test
    fun getSize_shouldNotChangeWhenElementsAreAddedTwice() {
        val trie = Trie()
        trie.add("a")
        trie.add("a")
        trie.add("zero")
        trie.add("hero")
        assertEquals(3, trie.size)
    }

    @Test
    fun getSize_shouldNotChangeWhenElementsAreAddedTwiceBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
            trie.add(i.toString())
        }
        assertEquals(1000, trie.size)
    }

    @Test
    fun getSize_shouldNotIgnoreEmptyStrings() {
        val trie = Trie()
        trie.add("")
        assertEquals(1, trie.size)
    }

    @Test
    fun add_shouldThrowIllegalArgumentExceptionIfElementWithNewlineIsAdded() {
        val trie = Trie()
        try {
            trie.add("eleme\nnt")
            fail("Should throw an IllegalArgumentException")
        } catch(e: IllegalArgumentException) {
            // do nothing
        }
    }

    @Test
    fun add_shouldReturnTrueIfNewElementIsAddedInEmptyTree() {
        val trie = Trie()
        assert(trie.add("aa"))
    }

    @Test
    fun add_shouldReturnTrueIfNewElementIsAddedBig() {
        val trie = Trie()
        var allElementsWereNew = true
        for (i in 0..1000) {
            allElementsWereNew = allElementsWereNew && trie.add(i.toString())
        }
        assert(allElementsWereNew)
    }

    @Test
    fun add_shouldReturnTrueIfEmptyStringIsAddedBug() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assert(trie.add(""))
    }

    @Test
    fun add_shouldReturnFalseIfElementIsNotAdded() {
        val trie = Trie()
        trie.add("121")
        assert(!trie.add("121"))
    }

    @Test
    fun add_shouldReturnFalseIfElementIsNotAddedBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assert(trie.add("154"))
    }

    @Test
    fun contains_shouldReturnTrueIfElementIsInTrie() {
        val trie = Trie()
        trie.add("1001")
        assert(trie.contains("1001"))
    }

    @Test
    fun contains_shouldReturnFalseIfElementIsNotInTrie() {
        val trie = Trie()
        trie.add("1001")
        assert(trie.contains("10010"))
    }

    @Test
    fun contains_shouldReturnTrueForAllElementsInTrieBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        for (i in 1000 downTo 0) {
            assert(trie.contains(i.toString()))
        }
    }

    @Test
    fun contains_shouldReturnFalseForAllElementsInTrieBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        for (i in 2000..3000) {
            assert(!trie.contains(i.toString()))
        }
    }

    @Test
    fun remove_shouldAlwaysReturnFalseOnEmptyTries() {
        val emptyTrie = Trie()
        assert(!emptyTrie.remove("ada"))
    }

    @Test
    fun remove_shouldReturnFalseOnIfElementIsNotRemoved() {
        val trie = Trie()
        trie.add("101")
        assert(!trie.remove("ada"))
    }

    @Test
    fun remove_shouldReturnTrueIfElementIsRemoved() {
        val trie = Trie()
        trie.add("151")
        assert(trie.remove("151"))
    }
    @Test
    fun remove_shouldReturnTrueIfElementIsRemovedBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assert(trie.remove("151"))
    }

    @Test
    fun remove_shouldReturnFalseIfElementIsNotRemovedBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add(i.toString())
        }
        assert(!trie.remove("1510"))
    }

    @Test
    fun howManyStartWithPrefix_shouldReturnReturnCorrectValuesForSmallTries() {
        val trie = Trie()
        trie.add("aab")
        trie.add("aac")
        trie.add("abc")
        trie.add("bbc")
        assertEquals(3, trie.howManyStartWithPrefix("a"))
    }

    @Test
    fun howManyStartWithPrefix_shouldReturnSizeForEmptyPrefix() {
        val trie = Trie()
        trie.add("aab")
        trie.add("aac")
        trie.add("abc")
        trie.add("bbc")
        assertEquals(4, trie.howManyStartWithPrefix(""))
    }

    @Test
    fun howManyStartWithPrefix_shouldReturnCorrectValueForBigTrees() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add("a$i")
        }
        for (i in 0..1000) {
            trie.add("b$i")
        }
        assertEquals(1001, trie.howManyStartWithPrefix("a"))
    }

    @Test
    fun howManyStartWithPrefix_shouldReturnSizeForEmptyPrefixBig() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add("a$i")
        }
        for (i in 0..1000) {
            trie.add("b$i")
        }
        assertEquals(2002, trie.howManyStartWithPrefix(""))
    }

    @Test
    fun empty_shouldRemoveAllValues() {
        val trie = Trie()
        for (i in 0..1000) {
            trie.add("a$i")
        }
        trie.empty()
        assertEquals(0, trie.size)
    }

    @Test
    fun getItems_shouldReturnAllItemsForBigTrees() {
        val trie = Trie()
        val expectedItems = mutableListOf<String>()
        for (i in 0..100) {
            trie.add("$i")
            expectedItems.add("$i")
        }
        assertEquals(expectedItems, trie.getItems().sorted())
    }

    @Test
    fun testToString() {

    }

    @Test
    fun testToString1() {
    }

    @Test
    fun serialize() {
    }

    @Test
    fun testSerialize() {
    }

    @Test
    fun deserialize() {
    }
}