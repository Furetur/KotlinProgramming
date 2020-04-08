package homeworks.hw1

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

fun factorialIterative(number: Int): Long {
    if (number < 0) {
        throw IllegalArgumentException("Factorial is defined for n >= 0")
    }
    if (number == 0) {
        return 1
    }
    return IntRange(1, number).fold(1) { acc: Long, curVal: Int -> acc * curVal }
}

fun factorialRecursive(number: Int, accumulator: Long = 1): Long {
    if (number < 0) {
        throw IllegalArgumentException("Factorial is defined for n >= 0")
    }
    if (number == 0) {
        return accumulator
    }

    return factorialRecursive(number - 1, accumulator * number)
}

fun main() {
    println("This program calculates n! Enter the n:")

    val inputString = readLine()

    if (inputString == null) {
        println("Empty input")
        return
    }

    val inputNumber: Int

    try {
        inputNumber = inputString.toInt()
    } catch (e: NumberFormatException) {
        println("The string you entered is not a number")
        return
    }

    println("Factorial calculated iteratively: ${factorialIterative(inputNumber)}")
    println("Factorial calculated recursively: ${factorialRecursive(inputNumber)}")
}
