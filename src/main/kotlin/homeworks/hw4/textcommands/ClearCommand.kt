package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

class ClearCommand(command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {

    companion object {
        const val name = "clear"
        const val requiredArgumentsNumber = 0
    }

    override fun apply(hashTable: HashTable<String, String>): String {
        hashTable.clear()
        return "Success! Hash table cleared"
    }
}
