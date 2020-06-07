package homeworks.hw4.textcommands

import homeworks.hw4.HashTable
import homeworks.hw4.TextCommandRunner.Companion.pearsonHashFunction
import homeworks.hw4.TextCommandRunner.Companion.polynomialHashFunction
import homeworks.hw4.TextCommandRunner.Companion.trivialHashFunction

class HashCommand(command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {

    companion object {
        const val name = "hash"
        const val requiredArgumentsNumber = 1

        val supportedHashFunctions = listOf(
            pearsonHashFunction,
            polynomialHashFunction,
            trivialHashFunction
        )
    }

    private val hashFunctionId = arguments[0].toIntOrNull()
        ?: throw IllegalCommandArgumentSyntax("Hash function id must be an integer")

    private var hashFunction = if (supportedHashFunctions.indices.contains(hashFunctionId - 1)) {
        supportedHashFunctions[hashFunctionId - 1]
    } else {
        throw IllegalCommandArgumentSyntax("This hash function is not supported")
    }

    override fun apply(hashTable: HashTable<String, String>): String {
        hashTable.hashFunction = hashFunction
        return "Success! Changed hash table's hash function"
    }
}
