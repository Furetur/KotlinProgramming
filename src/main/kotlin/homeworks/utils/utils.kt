package homeworks.utils

import java.lang.IllegalArgumentException

fun getMessageArguments(message: String, requiredArgumentsNumber: Int): List<String> {
    val tokens = message.split(" ")
    val arguments = tokens.subList(1, tokens.size)
    if (arguments.size < requiredArgumentsNumber) {
        throw IllegalArgumentException("Not enough arguments provided")
    }
    return arguments
}

fun getMessageIntArguments(message: String, requiredArgumentsNumber: Int): List<Int> {
    val arguments = getMessageArguments(message, requiredArgumentsNumber)
    val intArguments = arguments.map{ it.toIntOrNull() }
    if (intArguments.contains(null)) {
        throw IllegalArgumentException("Some of the arguments are parsable as Int")
    }
    return intArguments.filterNotNull()
}