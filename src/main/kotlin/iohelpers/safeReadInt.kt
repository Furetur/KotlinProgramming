package iohelpers

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

/**
 * Parses the string as an [Int] and returns the result or null
 * @return [Int] the parsed number
 * @throws [IllegalArgumentException] if it received null
 * @throws [NumberFormatException] if there was a number parsing error
 */
fun safeParseInt(str: String?): Int {
    if (str == null) {
        throw IllegalArgumentException("Received a null string")
    }

    try {
        return str.toInt()
    } catch (e: NumberFormatException) {
        throw e
    }
}