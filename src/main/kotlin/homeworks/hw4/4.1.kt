package homeworks.hw4

import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException

fun parseFileIntoPairs(file: File): MutableList<Pair<String, String>> {
    val pairs = mutableListOf<Pair<String, String>>()
    try {
        file.forEachLine {
            val parsedLine = it.split(':')
            val key = parsedLine[0].trim()
            val value = parsedLine[1].trim()
            val newPair = Pair(key, value)
            pairs.add(newPair)
        }
    } catch (e: FileNotFoundException) {
        throw e
    }
    return pairs
}

fun <K, V> putPairsIntoHashTable(hashTable: HashTable<K, V>, pairs: List<Pair<K, V>>) {
    for (pair in pairs) {
        hashTable[pair.first] = pair.second
    }
}

fun main() {
    val hashTable = HashTable<String, String>(pearsonHash)

    println("HashTable Interactive Mode:")
    println("get K                     --- get an item from the hash table or null if no item is found")
    println("put K V                   --- put (K, V) into the hash table")
    println("file FILENAME             --- populate hash table from a file that should have `key: value` lines")
    println("remove K                  --- remove key-value pair with this key from the hash table")
    println("clear                     --- remove everything from the hash table")
    println("hash HASH_FUNCTION_NUMBER --- change the hash function")
    println("stat                      --- get hash table stats")
    println("exit                      --- exits the program")
    println("Available hash functions:")
    for (i in supportedHashFunctions.indices) {
        println("${i + 1}) ${supportedHashFunctionsNames[i]}")
    }
    println("Default hash function: PearsonHash")

    var lastOutput = ""

    while (lastOutput != "exit") {
        val command = readLine() ?: break

        lastOutput = try {
            runInteractiveCommand(hashTable, command)
        } catch (e: IllegalArgumentException) {
            e.message ?: ""
        }
        println("> $lastOutput")
    }
}
