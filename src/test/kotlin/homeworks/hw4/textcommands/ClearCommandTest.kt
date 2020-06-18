package homeworks.hw4.textcommands

import org.junit.jupiter.api.Test

internal class ClearCommandTest : TextCommandTest(ClearCommand.name, ClearCommand.requiredArgumentsNumber) {
    override fun constructCommand(command: String): TextCommand<String, String> {
        return ClearCommand(command)
    }

    override val validArgument = "0"

    @Test
    fun `should clear the table`() {
        ClearCommand("clear").apply(populatedHashTable)
        assert(populatedHashTable.isEmpty())
    }
}
