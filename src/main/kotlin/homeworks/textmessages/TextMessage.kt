package homeworks.textmessages

import TicTacToeApp.Companion.FIELD_SIZE

open class TextMessage(
    name: String,
    requiredArgumentsNumber: Int,
    command: String
) {
    private val tokens = command.split(" ")
    protected val arguments = tokens.drop(1).mapNotNull { it.toIntOrNull() }

    init {
        if (tokens[0] != name) {
            throw IllegalCommandTypeException()
        }
        if (arguments.size != requiredArgumentsNumber) {
            throw IllegalNumberOfCommandArgumentsException()
        }
    }

    fun mustBePlayerId(integer: Int): Int {
        if (!(0..1).contains(integer)) {
            throw IllegalCommandArgumentSyntax("Must be a playerId, but was $integer")
        }
        return integer
    }

    fun mustBePlayerIdOrNegativeOne(integer: Int): Int {
        if (!(-1..1).contains(integer)) {
            throw IllegalCommandArgumentSyntax("Must be a playerId, but was $integer")
        }
        return integer
    }

    fun mustBePosition(integer: Int): Int {
        if (!(0 until FIELD_SIZE).contains(integer)) {
            throw IllegalCommandArgumentSyntax("Must be a field position, but was $integer")
        }
        return integer
    }

    class IllegalCommandTypeException : IllegalArgumentException()

    class IllegalNumberOfCommandArgumentsException : IllegalArgumentException()

    class IllegalCommandArgumentSyntax(msg: String) : java.lang.IllegalArgumentException(msg)
}
