package homeworks.hw1

import java.lang.IllegalArgumentException

fun countSubstringOccurrences(parentString: String, childString: String): Int {
    if (childString == "") {
        throw IllegalArgumentException("childString can not be an empty string")
    }

    val parentLength = parentString.length
    val childLength = childString.length

    var counter = 0
    for (parentIndex in 0..parentLength - childLength) {
        val substringInCParentString = parentString.substring(parentIndex, parentIndex + childLength)
        if (substringInCParentString == childString) {
            counter += 1
        }
    }
    return counter
}

fun main() {
    println("This programs count occurrences of a child string in the parent string")
    println("Enter the parent string")
    val parentString: String? = readLine()
    println("Enter the child string")
    val childString: String? = readLine()

    if (parentString == null || childString == null) {
        println("Empty strings are not allowed")
        return
    }

    try {
        val occurrences = countSubstringOccurrences(parentString, childString)
        println("Child string occurs in the parent string $occurrences time(s)")
    } catch (e: IllegalArgumentException) {
        println("The second string cannot be empty")
    }
}
