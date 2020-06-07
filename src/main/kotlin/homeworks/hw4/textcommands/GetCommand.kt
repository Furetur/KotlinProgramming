package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

class GetCommand(command: String) :
    TextCommand<String, String>("get", requiredArgumentsNumber, command) {

    companion object {
        const val name = "get"
        const val requiredArgumentsNumber = 1

        const val keyNotFoundResponse = "This key is not present in the hash table"
    }

    private val key = arguments[0]

    override fun apply(hashTable: HashTable<String, String>): String {
        val value = hashTable[key]
        return if (value == null) {
            keyNotFoundResponse
        } else {
            "The value for this key is '$value'"
        }
    }
}
