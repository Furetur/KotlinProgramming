package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

abstract class TextCommand<K, V>(
    name: String,
    requiredArgumentsNumber: Int,
    command: String
) {
    private val tokens = command.split(" ")
    protected val arguments = tokens.drop(1)

    init {
        if (tokens[0] != name) {
            throw IllegalCommandTypeException()
        }
        if (arguments.size != requiredArgumentsNumber) {
            throw IllegalNumberOfCommandArgumentsException()
        }
    }

    abstract fun apply(hashTable: HashTable<K, V>): String

    class IllegalCommandTypeException : IllegalArgumentException()

    class IllegalNumberOfCommandArgumentsException : IllegalArgumentException()

    class IllegalCommandArgumentSyntax(msg: String) : java.lang.IllegalArgumentException(msg)
}
