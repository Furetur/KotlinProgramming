package homeworks.hw2

fun <T> distinctRight(list: List<T>): List<T> {
    return list.reversed().distinct().reversed()
}

fun main() {
    println("Enter list and the program will remove all duplicate elements leaving only right occurrences")
    val inputList = readLine()?.split(' ')

    if (inputList == null) {
        println("A list should be provided")
        return
    }

    val inputListDistinct = distinctRight(inputList)

    println("List without duplicate elements")
    val outputStr = inputListDistinct.joinToString(" ")
    println(outputStr)
}
