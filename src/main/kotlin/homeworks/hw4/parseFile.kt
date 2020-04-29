package homeworks.hw4

import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException

val lineSyntax = Regex("^.+:.+$")

class WrongFileSyntax(msg: String) : IllegalArgumentException(msg)

fun parseLine(line: String): Pair<String, String> {
    if (!lineSyntax.matches(line)) {
        throw WrongFileSyntax("Expected: <not empty key>:<not empty value>, but received: $line")
    }
    val parsedLine = line.split(':')
    val key = parsedLine[0]
    val value = parsedLine[1]
    return Pair(key, value)
}

fun parseFile(file: File): MutableList<Pair<String, String>> {
    if (!file.exists()) {
        throw FileNotFoundException()
    }

    val pairs = mutableListOf<Pair<String, String>>()
    var lineNumber = 1
    file.forEachLine {
        try {
            pairs.add(parseLine(it))
        } catch (e: WrongFileSyntax) {
            throw WrongFileSyntax("On line $lineNumber:\n\t${e.message}")
        }
        lineNumber += 1
    }
    return pairs
}
