package homeworks.hw1

import java.io.File
import java.io.FileNotFoundException

const val DEFAULT_FILE_PATH = "./src/main/kotlin/homeworks/hw1/file.txt"

fun countMeaningfulStringsInFile(file: File): Int {
    if (!file.exists()) {
        throw FileNotFoundException()
    }
    var meaningfulLinesCount = 0
    file.forEachLine {
        if (it.isNotBlank()) {
            meaningfulLinesCount += 1
        }
    }
    return meaningfulLinesCount
}

fun main() {
    val file = File(DEFAULT_FILE_PATH)

    if (!file.exists()) {
        println("File $DEFAULT_FILE_PATH not found")
    } else {
        val numberOfMeaningfulLines = countMeaningfulStringsInFile(file)
        println("There are $numberOfMeaningfulLines meaningful lines in $DEFAULT_FILE_PATH")
    }
}
