package homeworks.hw1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.io.File
import java.io.FileNotFoundException

internal class _1_5KtTest {
    @Test
    fun `should consider empty line meaningless`() {
        assert(!isMeaningful(""))
    }

    @Test
    fun `should consider line that consists only of whitespaces meaningless`() {
        assert(!isMeaningful("        "))
    }

    @Test
    fun `should consider line that consists only of tabs meaningless`() {
        assert(!isMeaningful("\t\t\t\t\t"))
    }

    @Test
    fun `should consider line that consists only of whitespaces and tabs meaningless`() {
        assert(!isMeaningful("   \t   \t   \t\t  \t"))
    }
    @Test
    fun `should consider long line that consists only of whitespaces and tabs meaningless`() {
        val line = "   \t   \t   \t\t  \t".repeat(100)
        assert(!isMeaningful(line))
    }

    @Test
    fun `should consider line that has meaningful symbol meaningful`() {
        assert(isMeaningful("   \t   \t a  \t\t  \t"))
    }
    @Test
    fun `should consider long line that has meaningful symbol meaningful`() {
        val line = "   \t   \t   \t\t  \t".repeat(100) + "s"
        assert(isMeaningful(line))
    }

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
        try {
            countMeaningfulStringsInFile(file)
            fail("Should throw")
        } catch (e: FileNotFoundException) {
            // success
        }
    }
}