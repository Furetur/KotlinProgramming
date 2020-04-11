package homeworks.hw4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.io.File
import java.io.FileNotFoundException

internal class _4_1KtTest {
    @Test
    fun `should throw if file does not exist`() {
        val file = File("doesnotexist.txt")
        try {
            parseFileIntoPairs(file)
            fail("Should throw FileNotFoundException")
        } catch (e: FileNotFoundException) {
            // success
        }
    }

    @Test
    fun `should correctly parse the first file`() {
        val expectedPairs = listOf(Pair("k1", "v1"), Pair("k2", "v2"), Pair("k3", "v3"), Pair("k4", "v4"))
        val file = File("./src/test/kotlin/homeworks/hw4/test1.txt")
        val actualPairs = parseFileIntoPairs(file)
        assertEquals(expectedPairs, actualPairs)
    }

    @Test
    fun `should correctly parse the second file`() {
        val expectedPairs = listOf(Pair("k1", "v1"), Pair("k2", "v2"), Pair("k3", "v3"),
            Pair("k4", "v4"), Pair("k5", "v5"), Pair("k6", "v6"))
        val file = File("./src/test/kotlin/homeworks/hw4/test2.txt")
        val actualPairs = parseFileIntoPairs(file)
        assertEquals(expectedPairs, actualPairs)
    }

    @Test
    fun `put pairs should put entries`() {
        val hashTable = HashTable<String, String>(pearsonHash)
        val expectedPairs = mutableSetOf<Pair<String, String>>()
        for (i in 0..100) {
            val pair = Pair("key$i", "val$i")
            expectedPairs.add(pair)
        }
        putPairsIntoHashTable(hashTable, expectedPairs.toList())
        val actualPairs = hashTable.entries.map { entry -> Pair(entry.key, entry.value) }.toMutableSet()
        assertEquals(expectedPairs, actualPairs)
    }
}