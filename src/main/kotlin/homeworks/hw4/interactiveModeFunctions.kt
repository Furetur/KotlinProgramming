package homeworks.hw4

import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException

const val THREE_COMMANDS_NUMBER = 3

fun get(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size < 2) {
        throw IllegalArgumentException("Syntax: get K")
    }
    val key = commandTokens[1]
    return hashTable[key] ?: "undefined"
}

fun put(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size < THREE_COMMANDS_NUMBER) {
        throw IllegalArgumentException("Syntax: put K V")
    }
    val key = commandTokens[1]
    val value = commandTokens[2]
    val prevValue = hashTable.put(key, value)
    return prevValue ?: "undefined"
}

fun file(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size < 2) {
        throw IllegalArgumentException("Syntax: file FILENAME")
    }
    val filename = commandTokens[1]
    val file = File("./src/main/kotlin/homeworks/hw4/$filename")
    val pairs = try {
        parseFileIntoPairs(file)
    } catch (e: FileNotFoundException) {
        throw IllegalArgumentException("File not found")
    }
    putPairsIntoHashTable(hashTable, pairs)
    return "put ${pairs.size} pairs"
}

fun remove(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size < 2) {
        throw IllegalArgumentException("Syntax: remove K")
    }
    val value = hashTable.remove(commandTokens[1])
    return value ?: "undefined"
}

fun clear(hashTable: HashTable<String, String>): String {
    hashTable.clear()
    return "OK"
}

fun hash(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size < 2) {
        throw IllegalArgumentException("Syntax: hash HASH_FUNCTION_NUMBER")
    }
    val hashFunctionNumber = commandTokens[1].toIntOrNull()
    if (hashFunctionNumber == null || hashFunctionNumber > supportedHashFunctions.size) {
        throw IllegalArgumentException("Syntax: hash HASH_FUNCTION_NUMBER")
    }
    val selectedHashFunction = supportedHashFunctions[hashFunctionNumber - 1]
    hashTable.hashFunction = selectedHashFunction
    return supportedHashFunctionsNames[hashFunctionNumber - 1]
}

fun stat(hashTable: HashTable<String, String>): String {
    return "Occupied Cells: ${hashTable.occupiedCellsCount}. Load factor: ${hashTable.loadFactor}. " +
            "Max conflicts in one cell: ${hashTable.maxConflictsForKey}"
}

fun runInteractiveCommand(hashTable: HashTable<String, String>, command: String): String {
    val commandTokens = command.split(' ')
    return when (commandTokens[0]) {
        "get" -> get(hashTable, commandTokens)
        "put" -> put(hashTable, commandTokens)
        "file" -> file(hashTable, commandTokens)
        "remove" -> remove(hashTable, commandTokens)
        "clear" -> clear(hashTable)
        "hash" -> hash(hashTable, commandTokens)
        "exit" -> "exit"
        "stat" -> stat(hashTable)
        else -> throw IllegalArgumentException("Unknown command")
    }
}
