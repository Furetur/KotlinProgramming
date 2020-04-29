package homeworks.hw4.task2

import java.io.File

const val FILENAME = "file.txt"

fun readFirstLineFromFile(file: File): String {
    return file.bufferedReader().use { it.readLine() }
}

fun main() {
    println("This program reads data from a text file. And parses its data as an expression tree")

    val file = File("./src/main/kotlin/homeworks/hw4/task2/$FILENAME")

    if (!file.exists()) {
        println("File not found")
        return
    }

    println("Reading from $FILENAME")

    val input = readFirstLineFromFile(file)

    try {
        val tree = parseExpressionTree(input)
        println("Received tree: $tree")
        println("Tree evaluates to: ${tree.evaluate()}")
    } catch (e: IllegalStringSyntax) {
        println("Wrong syntax of $FILENAME:\n\t${e.message}")
    } catch (e: ExpressionTree.UnsupportedOperatorException) {
        println("Wrong syntax of $FILENAME:\n\t${e.message}")
    }
}
