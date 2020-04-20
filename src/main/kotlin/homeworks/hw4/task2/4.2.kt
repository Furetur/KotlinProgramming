package homeworks.hw4.task2

import java.io.File

const val PATH_PREFIX = "./src/main/kotlin/homeworks/hw4/task2"

fun readFirstLineFromFile(file: File): String {
    return file.bufferedReader().use { it.readLine() }
}

fun main() {
    println("This program reads data from a text file. And parses its data as an expression tree")
    println("Enter filename (file should be in src/main/kotlin/homeworks/hw4/task2)")

    val filename = readLine() ?: "file.txt"

    val file = File("$PATH_PREFIX/$filename")

    if (!file.exists()) {
        println("File not found")
        return
    }

    println("Reading from $filename")

    val input = readFirstLineFromFile(file)

    val tree = try {
        parseExpressionTree(input)
    } catch (e: ExpressionTree.IllegalStringSyntax) {
        println("Wrong syntax of $filename:\n\t${e.message}")
        return
    }

    println("Received tree: $tree")
    println("Tree evaluates to: ${tree.evaluate()}")
}
