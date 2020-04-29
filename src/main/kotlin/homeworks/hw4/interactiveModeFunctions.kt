package homeworks.hw4

import java.io.File
import java.lang.IllegalArgumentException

const val THREE_COMMANDS_NUMBER = 3

val repeatedWhiteSpace = Regex(" ( )+")

fun get(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size != 2) {
        throw IllegalArgumentException("Syntax: get K")
    }
    val key = commandTokens[1]
    return hashTable[key] ?: "undefined"
}

fun put(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size != THREE_COMMANDS_NUMBER) {
        throw IllegalArgumentException("Syntax: put K V")
    }
    val key = commandTokens[1]
    val value = commandTokens[2]
    val prevValue = hashTable.put(key, value)
    return if (prevValue == null) {
        "put ($key, $value). This key previously was not in the table"
    } else {
        "put ($key, $value). Previous value for this key was \"$prevValue\""
    }
}

fun file(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size != 2) {
        throw IllegalArgumentException("Syntax: file FILENAME")
    }
    val filename = commandTokens[1]
    val file = File("./src/main/kotlin/homeworks/hw4/$filename")
    if (!file.exists()) {
        throw IllegalArgumentException("File not found")
    }
    val pairs = parseFile(file)
    putPairsIntoHashTable(hashTable, pairs)
    return "put ${pairs.size} pairs: $pairs"
}

fun remove(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size != 2) {
        throw IllegalArgumentException("Syntax: remove K")
    }
    val key = commandTokens[1]
    val value = hashTable.remove(key)
    return if (value == null) "This key is not present in the hash table" else "Removed ($key, $value)"
}

fun clear(hashTable: HashTable<String, String>): String {
    hashTable.clear()
    return "OK"
}

fun hash(hashTable: HashTable<String, String>, commandTokens: List<String>): String {
    if (commandTokens.size != 2) {
        throw IllegalArgumentException("Syntax: hash HASH_FUNCTION_NUMBER")
    }
    val hashFunctionNumber = commandTokens[1].toIntOrNull()
    if (hashFunctionNumber == null || hashFunctionNumber > supportedHashFunctions.size) {
        throw IllegalArgumentException(
            "Syntax: hash HASH_FUNCTION_NUMBER.\n\tProvide a hash function number. Refer to the list of hash functions"
        )
    }
    val selectedHashFunction = supportedHashFunctions[hashFunctionNumber - 1]
    hashTable.hashFunction = selectedHashFunction
    return supportedHashFunctionsNames[hashFunctionNumber - 1]
}

fun stat(hashTable: HashTable<String, String>): String {
    return "Occupied Cells: ${hashTable.occupiedCellsCount}. Load factor: ${hashTable.loadFactor}. " +
            "Max conflicts in one cell: ${hashTable.maxConflictsForKey}"
}

fun splitCommandIntoTokens(command: String): List<String> {
    if (repeatedWhiteSpace.containsMatchIn(command)) {
        throw IllegalArgumentException("Command cannot have multiple whitespaces next to each other.")
    }
    return command.split(' ')
}

fun runInteractiveCommand(hashTable: HashTable<String, String>, command: String): String {
    val commandTokens = splitCommandIntoTokens(command.trim())
    return when (commandTokens[0]) {
        "get" -> get(hashTable, commandTokens)
        "put" -> put(hashTable, commandTokens)
        "file" -> file(hashTable, commandTokens)
        "remove" -> remove(hashTable, commandTokens)
        "clear" -> clear(hashTable)
        "hash" -> hash(hashTable, commandTokens)
        "exit" -> "exit"
        "stat" -> stat(hashTable)
        else -> throw IllegalArgumentException("Unknown command \"${commandTokens[0]}\"")
    }
}
