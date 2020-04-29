package homeworks.hw4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class InteractiveModeFunctionsKtTest {
    private val hashTable = HashTable<String, String>(pearsonHash)

    @BeforeEach
    fun clearHashTable() {
        hashTable.clear()
    }

    @Test
    fun `get should return undefined if value is not in the table`() {
        val expected = "undefined"
        val actual = runInteractiveCommand(hashTable, "get a")
        assertEquals(expected, actual)
    }

    @Test
    fun `get should return value if value is in the table`() {
        hashTable["a"] = "b"
        val expected = "b"
        val actual = runInteractiveCommand(hashTable, "get a")
        assertEquals(expected, actual)
    }

    @Test
    fun `get should throw if not enough arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "get")
        }
    }

    @Test
    fun `get should throw if too many arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "get a b c d")
        }
    }

    @Test
    fun `put should put value in the table`() {
        val command = "put k v"
        runInteractiveCommand(hashTable, command)
        assertEquals("v", hashTable["k"])
    }

    @Test
    fun `put say that value is not present if it was not present`() {
        val command = "put k v"
        val expected = "put (k, v). This key previously was not in the table"
        val actual = runInteractiveCommand(hashTable, command)
        assertEquals(expected, actual)
    }

    @Test
    fun `put should return previous value if it was present`() {
        hashTable["k"] = "prev-v"
        val command = "put k v"
        val expected = "put (k, v). Previous value for this key was \"prev-v\""
        val actual = runInteractiveCommand(hashTable, command)
        assertEquals(expected, actual)
    }

    @Test
    fun `put should throw if not enough arguments provided 1`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "put")
        }
    }

    @Test
    fun `put should throw if not enough arguments provided 2`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "put k")
        }
    }

    @Test
    fun `put should throw if too many arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "put k v n d")
        }
    }

    @Test
    fun `remove should remove keys from the table`() {
        hashTable["key"] = "value"
        runInteractiveCommand(hashTable, "remove key")
        assertFalse(hashTable.containsKey("key"))
    }

    @Test
    fun `remove should say if a pair was not removed`() {
        val expected = "This key is not present in the hash table"
        val actual = runInteractiveCommand(hashTable, "remove key")
        assertEquals(expected, actual)
    }

    @Test
    fun `remove should say if a pair was removed`() {
        hashTable["key"] = "value"
        val expected = "Removed (key, value)"
        val actual = runInteractiveCommand(hashTable, "remove key")
        assertEquals(expected, actual)
    }

    @Test
    fun `remove should throw if not enough arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "remove")
        }
    }

    @Test
    fun `remove should throw if too many arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "remove a b")
        }
    }

    @Test
    fun `clear should clear the hash table`() {
        hashTable["k"] = "v"
        runInteractiveCommand(hashTable, "clear")
        assert(hashTable.isEmpty())
    }

    @Test
    fun `hash should change the hash function`() {
        runInteractiveCommand(hashTable, "hash 3")
        assertEquals(trivialHash, hashTable.hashFunction)
    }

    @Test
    fun `hash should throw if not enough arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "hash")
        }
    }

    @Test
    fun `hash should throw if too many arguments provided`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "hash a b")
        }
    }

    @Test
    fun `hash should throw if hash function does not exist`() {
        assertThrows(IllegalArgumentException::class.java) {
            runInteractiveCommand(hashTable, "hash 7")
        }
    }
}
