package homeworks.hw4.textcommands

import homeworks.hw4.hashfunctions.PolynomialHashFunction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

internal class HashCommandTest : TextCommandTest(HashCommand.name, HashCommand.requiredArgumentsNumber) {
    override fun constructCommand(command: String): TextCommand<String, String> {
        return HashCommand(command)
    }

    override val validArgument = "0"

    fun `should throw if argument is not an integer`() {
        val command = "hash s"
        assertThrows(TextCommand.IllegalCommandArgumentSyntax::class.java) {
            HashCommand(command)
        }
    }

    fun `should change the hash function`() {
        val command = HashCommand("hash 1")
        command.apply(populatedHashTable)
        assertEquals(PolynomialHashFunction::class, populatedHashTable.hashFunction::class)
    }
}
