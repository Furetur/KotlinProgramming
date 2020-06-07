package homeworks.hw4.textcommands

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PutCommandTest : TextCommandTest("put", 2) {
    override fun constructCommand(command: String): TextCommand<String, String> {
        return PutCommand(command)
    }

    override val validArgument = "k"

    @Test
    fun `should put keys into the table`() {
        val command = PutCommand("put k v")
        command.apply(emptyHashTable)
        assertEquals("v", emptyHashTable["k"])
    }

    @Test
    fun `should return key not found response if key was not present in the table`() {
        val command = PutCommand("put k v")
        val response = command.apply(emptyHashTable)
        assertEquals(PutCommand.keyNotFoundResponse, response)
    }

    @Test
    fun `should return a message that contains the previous value`() {
        val command = PutCommand("put key50 val10")
        val response = command.apply(populatedHashTable)
        assert(response.contains("val50"))
    }
}
