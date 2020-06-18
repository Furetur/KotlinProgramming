package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

class ExitCommand(command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {

    companion object {
        const val name = "exit"
        const val requiredArgumentsNumber = 0
    }

    override fun apply(hashTable: HashTable<String, String>): String = "exit"
}
