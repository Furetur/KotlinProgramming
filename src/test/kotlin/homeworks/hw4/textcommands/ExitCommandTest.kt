package homeworks.hw4.textcommands

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ExitCommandTest : TextCommandTest(ExitCommand.name, ExitCommand.requiredArgumentsNumber) {

    override val validArgument = "0"
    override fun constructCommand(command: String): TextCommand<String, String> {
        return ExitCommand(command)
    }

    @Test
    fun `should return exit`() {
        assertEquals("exit", ExitCommand("exit").apply(emptyHashTable))
    }
}
