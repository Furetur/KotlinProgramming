package homeworks.hw4.textcommands

import homeworks.hw4.HashTable
import homeworks.hw4.TextCommandRunner.Companion.defaultHashFunction
import homeworks.hw4.repopulateHashTable
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal abstract class TextCommandTest(private val commandName: String, private val requiredArgumentsNumber: Int) {

    abstract fun constructCommand(command: String): TextCommand<String, String>

    abstract val validArgument: String

    protected var emptyHashTable = HashTable<String, String>(defaultHashFunction)
    protected var populatedHashTable = HashTable<String, String>(defaultHashFunction)

    @BeforeEach
    fun resetHashTables() {
        emptyHashTable.clear()
        repopulateHashTable(populatedHashTable, 100)
    }

    private fun getStringCommandWithCorrectName(argumentsNumber: Int): String {
        val arguments = List(argumentsNumber) { validArgument }
        return if (arguments.isEmpty()) {
            commandName
        } else {
            "$commandName ${arguments.joinToString(" ")}"
        }
    }

    @Test
    fun `should throw if is given more than requiredArgumentsNumber arguments`() {
        val command = getStringCommandWithCorrectName(requiredArgumentsNumber + 1)
        assertThrows(TextCommand.IllegalNumberOfCommandArgumentsException::class.java) {
            constructCommand(command)
        }
    }

    @Test
    fun `should throw if is given less than requiredArgumentsNumber arguments`() {
        if (requiredArgumentsNumber == 0) {
            return
        }
        val command = getStringCommandWithCorrectName(requiredArgumentsNumber - 1)
        assertThrows(TextCommand.IllegalNumberOfCommandArgumentsException::class.java) {
            constructCommand(command)
        }
    }

    @Test
    fun `should throw if command name is not correct`() {
        if (requiredArgumentsNumber == 0) {
            return
        }
        val arguments = List(requiredArgumentsNumber) { "0" }
        val incorrectCommandName = commandName + "x"
        val command = "$incorrectCommandName ${arguments.joinToString(" ")}"
        assertThrows(TextCommand.IllegalCommandTypeException::class.java) {
            constructCommand(command)
        }
    }
}
