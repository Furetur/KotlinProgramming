package homeworks.hw1

fun <T> mutateSwapListPartitions(list: MutableList<T>, partitionSize1: Int, partitionSize2: Int) {
    if (partitionSize1 + partitionSize2 != list.size) {
        throw IllegalArgumentException("Wrong partition sizes passed")
    }
    list.reverse()
    list.subList(0, partitionSize2).reverse()
    list.subList(partitionSize2, list.size).reverse()
}

fun main() {
    println("Enter 2 partition sizes of your array:")

    val partitionSize1 = readLine()?.toIntOrNull()
    val partitionSize2 = readLine()?.toIntOrNull()

    println("Enter your array with spaces separating elements")

    val inputArr: MutableList<String>? = readLine()?.split(" ")?.toMutableList()

    if (inputArr == null || partitionSize1 == null || partitionSize2 == null) {
        print("Wrong input. You must enter 2 integer numbers, and then a list")
        return
    }

    try {
        mutateSwapListPartitions(inputArr, partitionSize1, partitionSize2)
        println("Updated array:")
        val outputStr = inputArr.joinToString(" ")
        println(outputStr)
    } catch (e: java.lang.IllegalArgumentException) {
        println("The sum of partition sizes should add up to the list's size")
    }
    return
}
