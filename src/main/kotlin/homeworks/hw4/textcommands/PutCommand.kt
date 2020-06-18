package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

class PutCommand(command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {

    companion object {
        const val name = "put"
        const val requiredArgumentsNumber = 2

        const val keyNotFoundResponse = "Success! This key was not present in the hash table"
    }

    val key = arguments[0]
    val value = arguments[1]

    override fun apply(hashTable: HashTable<String, String>): String {
        val prevValue = hashTable.put(key, value)
        return if (prevValue == null) {
            keyNotFoundResponse
        } else {
            "Success! Previous value for this key was '$prevValue'"
        }
    }
}
