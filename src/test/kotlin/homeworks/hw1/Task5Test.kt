package homeworks.hw1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.io.File
import java.io.FileNotFoundException

internal class Task5Test {
    @Test
    fun `should work for empty files`() {
        val emptyFile = File("./src/test/kotlin/homeworks/hw1/emptytest.txt")
        assertEquals(0, countMeaningfulStringsInFile(emptyFile))
    }

    @Test
    fun `should count lines that consist only of tabs as maningless`() {
        val emptyFile = File("./src/test/kotlin/homeworks/hw1/tabtest.txt")
        assertEquals(3, countMeaningfulStringsInFile(emptyFile))
    }

    @Test
    fun `should count lines that consist only of whitespaces as maningless`() {
        val emptyFile = File("./src/test/kotlin/homeworks/hw1/whitespacetest.txt")
        assertEquals(2, countMeaningfulStringsInFile(emptyFile))
    }

    @Test
    fun `should work for big test`() {
        val emptyFile = File("./src/test/kotlin/homeworks/hw1/bigtest.txt")
        assertEquals(300, countMeaningfulStringsInFile(emptyFile))
    }

    @Test
    fun `should throw FileNotFoundException if file does not exist`() {
        val file = File("doesnotexist")
        assertThrows(FileNotFoundException::class.java) {
            countMeaningfulStringsInFile(file)
        }
    }
}
