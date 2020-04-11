package homeworks.hw1

import java.io.File
import java.io.FileNotFoundException

const val DEFAULT_PATH_PREFIX = "./src/main/kotlin/homeworks/hw1"
val notMeaningfulSymbols = listOf(' ', '\t', '\n')

fun isMeaningful(str: String): Boolean {
    for (char in str) {
        if (!notMeaningfulSymbols.contains(char)) {
            return true
        }
    }
    return false
}

fun countMeaningfulStringsInFile(file: File): Int {
    if (!file.exists()) {
        throw FileNotFoundException()
    }
    var meaningfulLinesCount = 0
    file.forEachLine {
        if (isMeaningful(it)) {
            meaningfulLinesCount += 1
        }
    }
    return meaningfulLinesCount
}

fun main() {
    println("Enter filename and this will calculate number of meaningful lines")
    val filename = readLine() ?: "file.txt"
    val path = "$DEFAULT_PATH_PREFIX/$filename"
    val file = File(path)

    if (!file.exists()) {
        println("File not found")
    } else {
        val numberOfMeaningfulLines = countMeaningfulStringsInFile(file)
        println("There are $numberOfMeaningfulLines meaningful lines in $path")
    }
}
