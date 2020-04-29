package homeworks.hw4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Task1Test {
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
