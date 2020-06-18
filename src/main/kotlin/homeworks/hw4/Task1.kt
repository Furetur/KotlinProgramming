package homeworks.hw4

import homeworks.hw4.TextCommandRunner.Companion.defaultHashFunction
import homeworks.hw4.textcommands.ExitCommand
import homeworks.hw4.textcommands.HashCommand.Companion.supportedHashFunctions
import homeworks.hw4.textcommands.TextCommand
import java.io.FileNotFoundException

fun main() {
    val commandRunner = TextCommandRunner()

    println("HashTable Interactive Mode:")
    println("get K                     --- get an item from the hash table or null if no item is found")
    println("put K V                   --- put (K, V) into the hash table")
    println("file FILENAME             --- populate hash table from a file that should have `key: value` lines")
    println("remove K                  --- remove key-value pair with this key from the hash table")
    println("clear                     --- remove everything from the hash table")
    println("hash HASH_FUNCTION_NUMBER --- change the hash function")
    println("stat                      --- get hash table stats")
    println("exit                      --- exits the program")
    println("Available hash functions:")
    for (i in supportedHashFunctions.indices) {
        println("${i + 1}) ${supportedHashFunctions[i].name}")
    }
    println("Default hash function: ${defaultHashFunction.name}")

    var lastOutput = ""

    while (lastOutput != ExitCommand.name) {
        val command = readLine() ?: break

        lastOutput = try {
            commandRunner.runCommand(command)
        } catch (e: TextCommand.IllegalCommandTypeException) {
            "Error: this command is not supported"
        } catch (e: TextCommand.IllegalNumberOfCommandArgumentsException) {
            "Error: the number of arguments you entered is not correct"
        } catch (e: TextCommand.IllegalCommandArgumentSyntax) {
            "Error: argument you entered does not follow the correct syntax \n\t ${e.message}"
        } catch (e: FileNotFoundException) {
            "Error: file not found"
        } catch (e: WrongFileSyntax) {
            "Error: file syntax is not valid \n\t ${e.message}"
        }
        println("> $lastOutput")
    }
}
