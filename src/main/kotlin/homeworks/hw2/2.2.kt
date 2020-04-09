package homeworks.hw2

import java.lang.IllegalArgumentException

fun <T> distinctRight(list: List<T>): List<T> {
    return list.reversed().distinct().reversed()
}

fun main() {
    println("Enter list and the program will remove all duplicate elements leaving only right occurrences")
    val inputList = readLine()?.split(' ')?.map { it.toInt() } ?: throw IllegalArgumentException("Wrong input")
    val inputListDistinct = distinctRight(inputList)

    println("List without duplicate elements")
    for (element in inputListDistinct) {
        print("$element ")
    }
}
