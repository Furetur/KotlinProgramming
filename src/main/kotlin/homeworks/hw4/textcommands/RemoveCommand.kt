package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

class RemoveCommand(command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "remove"
        const val requiredArgumentsNumber = 1

        const val keyNotFoundResponse = "This key is not present in the hash table"
    }

    private val key = arguments[0]

    override fun apply(hashTable: HashTable<String, String>): String {
        val value = hashTable.remove(key)
        return if (value == null) {
            keyNotFoundResponse
        } else {
            "Success! The value for this key was '$value'"
        }
    }
}
