package homeworks.hw4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileNotFoundException

internal class ParseFileKtTest {
    @Test
    fun `should throw if line does not contain a colon`() {
        val line = "234567890"
        assertThrows(WrongFileSyntax::class.java) {
            parseLine(line)
        }
    }

    @Test
    fun `should throw if line contains an empty key`() {
        val line = ":456789"
        assertThrows(WrongFileSyntax::class.java) {
            parseLine(line)
        }
    }

    @Test
    fun `should throw if line contains an empty value`() {
        val line = "456789:"
        assertThrows(WrongFileSyntax::class.java) {
            parseLine(line)
        }
    }

    @Test
    fun `should parse line that contains both key and value`() {
        val line = "a:b"
        val expected = Pair("a", "b")
        val actual = parseLine(line)
        assertEquals(expected, actual)
    }

    @Test
    fun `should not trim key`() {
        val line = "  a  :b"
        val expected = Pair("  a  ", "b")
        val actual = parseLine(line)
        assertEquals(expected, actual)
    }

    @Test
    fun `should not trim value`() {
        val line = "a:  b  "
        val expected = Pair("a", "  b  ")
        val actual = parseLine(line)
        assertEquals(expected, actual)
    }

    @Test
    fun `should throw if file does not exist`() {
        val file = File("doesnotexist.txt")
        assertThrows(FileNotFoundException::class.java) {
            parseFile(file)
        }
    }

    @Test
    fun `should correctly parse the file without syntax errors`() {
        val expectedPairs = listOf(
            Pair("k1", " v1"),
            Pair("k2", "v2"),
            Pair("k3", "v3"),
            Pair("k4", "     v4")
        )
        val file = File("./src/test/kotlin/homeworks/hw4/test1.txt")
        val actualPairs = parseFile(file)
        assertEquals(expectedPairs, actualPairs)
    }

    @Test
    fun `should correctly parse empty files`() {
        val file = File("./src/test/kotlin/homeworks/hw4/empty.txt")
        val actualPairs = parseFile(file)
        assertEquals(0, actualPairs.size)
    }

    @Test
    fun `should throw if file contains syntax errors`() {
        val file = File("./src/test/kotlin/homeworks/hw4/file3.txt")
        assertThrows(WrongFileSyntax::class.java) {
            parseFile(file)
        }
    }
}
