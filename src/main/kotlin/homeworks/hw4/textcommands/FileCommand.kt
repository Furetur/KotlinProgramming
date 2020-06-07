package homeworks.hw4.textcommands

import homeworks.hw4.HashTable
import homeworks.hw4.parseFile
import java.io.File
import java.io.FileNotFoundException

class FileCommand(val command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {

    companion object {
        const val name = "file"
        const val requiredArgumentsNumber = 1
    }

    private val filename = arguments[0]
    private val filepath = "./src/main/resources/homeworks/hw4/$filename"

    override fun apply(hashTable: HashTable<String, String>): String {
        val file = File(filepath)
        if (!file.exists()) {
            throw FileNotFoundException()
        }
        val pairs = parseFile(file)
        hashTable.putAll(pairs)
        return "Success! Put ${pairs.size} pairs: $pairs"
    }
}
