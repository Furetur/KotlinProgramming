package homeworks.hw4

import java.io.File
import java.lang.IllegalArgumentException

private val lineSyntax = Regex("^.+:.+$")

fun parseLine(line: String): Pair<String, String> {
    if (!lineSyntax.matches(line)) {
        throw WrongFileSyntax("Expected: <not empty key>:<not empty value>, but received: '$line'")
    }
    val parsedLine = line.split(':')
    val key = parsedLine[0]
    val value = parsedLine[1]
    return Pair(key, value)
}

fun parseFile(file: File): MutableList<Pair<String, String>> {
    val pairs = mutableListOf<Pair<String, String>>()
    var lineNumber = 1
    file.forEachLine {
        try {
            pairs.add(parseLine(it))
        } catch (e: WrongFileSyntax) {
            throw WrongFileSyntax("On line $lineNumber:\n\t\t${e.message}")
        }
        lineNumber += 1
    }
    return pairs
}

class WrongFileSyntax(msg: String) : IllegalArgumentException(msg)
