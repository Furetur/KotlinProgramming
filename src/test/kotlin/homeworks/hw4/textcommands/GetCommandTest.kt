package homeworks.hw4.textcommands

import homeworks.hw4.textcommands.GetCommand.Companion.keyNotFoundResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetCommandTest : TextCommandTest(GetCommand.name, GetCommand.requiredArgumentsNumber) {

    override fun constructCommand(command: String): TextCommand<String, String> {
        return GetCommand(command)
    }

    override val validArgument = "key"

    @Test
    fun `should return a keyNotFoundResponse if key is not present in the table`() {
        val getCommand = GetCommand("get k")
        val response = getCommand.apply(populatedHashTable)
        assertEquals(keyNotFoundResponse, response)
    }

    @Test
    fun `should return a message that contains the value if key is present in the table`() {
        val getCommand = GetCommand("get key50")
        val response = getCommand.apply(populatedHashTable)
        assert(response.contains("val50"))
    }
}
