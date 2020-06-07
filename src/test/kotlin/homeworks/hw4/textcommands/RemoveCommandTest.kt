package homeworks.hw4.textcommands

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class RemoveCommandTest : TextCommandTest(RemoveCommand.name, RemoveCommand.requiredArgumentsNumber) {
    override fun constructCommand(command: String): TextCommand<String, String> {
        return RemoveCommand(command)
    }

    override val validArgument = "k"

    @Test
    fun `should remove key`() {
        val command = RemoveCommand("remove key1")
        command.apply(populatedHashTable)
        assertFalse(populatedHashTable.containsKey("key1"))
    }

    @Test
    fun `should return key not found response if key was not present in the table`() {
        val command = RemoveCommand("remove k")
        val response = command.apply(emptyHashTable)
        assertEquals(RemoveCommand.keyNotFoundResponse, response)
    }

    @Test
    fun `should return a message that contains the previous value`() {
        val command = RemoveCommand("remove key50")
        val response = command.apply(populatedHashTable)
        assert(response.contains("val50"))
    }
}
