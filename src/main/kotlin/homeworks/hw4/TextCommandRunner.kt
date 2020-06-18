package homeworks.hw4

import homeworks.hw4.hashfunctions.PearsonHashFunction
import homeworks.hw4.hashfunctions.PolynomialHashFunction
import homeworks.hw4.hashfunctions.TrivialHashFunction
import homeworks.hw4.textcommands.GetCommand
import homeworks.hw4.textcommands.PutCommand
import homeworks.hw4.textcommands.ClearCommand
import homeworks.hw4.textcommands.RemoveCommand
import homeworks.hw4.textcommands.HashCommand
import homeworks.hw4.textcommands.FileCommand
import homeworks.hw4.textcommands.ExitCommand
import homeworks.hw4.textcommands.StatCommand
import homeworks.hw4.textcommands.TextCommand

class TextCommandRunner {

    companion object {
        const val HASH_MODULO = 53
        const val POLYNOMIAL_HASH_PRIME = 131
        const val PEARSON_RESOLUTION = 256
        const val TRIVIAL_HASH_SHIFT_SIZE = 8

        val pearsonHashFunction = PearsonHashFunction(PEARSON_RESOLUTION, HASH_MODULO)
        val polynomialHashFunction = PolynomialHashFunction(POLYNOMIAL_HASH_PRIME, HASH_MODULO)
        val trivialHashFunction = TrivialHashFunction(TRIVIAL_HASH_SHIFT_SIZE, HASH_MODULO)
        val defaultHashFunction = pearsonHashFunction
    }

    private val hashTable = HashTable<String, String>(defaultHashFunction)

    fun runCommand(command: String): String {
        val textCommand = constructCommand(command)
        return textCommand.apply(hashTable)
    }

    private fun constructCommand(command: String): TextCommand<String, String> {
        return when {
            command.startsWith("get") -> GetCommand(command)
            command.startsWith("put") -> PutCommand(command)
            command.startsWith("remove") -> RemoveCommand(command)
            command.startsWith("clear") -> ClearCommand(command)
            command.startsWith("hash") -> HashCommand(command)
            command.startsWith("file") -> FileCommand(command)
            command.startsWith("stat") -> StatCommand(command)
            command.startsWith("exit") -> ExitCommand(command)
            else -> throw TextCommand.IllegalCommandTypeException()
        }
    }
}
