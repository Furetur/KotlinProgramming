package homeworks.hw1

fun <T> mutateReverse(list: MutableList<T>, startIndex: Int = 0, endIndex: Int = list.size - 1) {
    var left: Int = startIndex
    var right: Int = endIndex
    while (left < right) {
        val temp: T = list[left]
        list[left] = list[right]
        list[right] = temp
        left++
        right--
    }
}

fun <T> mutateSwapListPartitions(list: MutableList<T>, partitionSize1: Int, partitionSize2: Int) {
    if (partitionSize1 + partitionSize2 != list.size) {
        throw IllegalArgumentException("Wrong partition sizes passed")
    }
    mutateReverse(list)
    mutateReverse(list, 0, partitionSize2 - 1)
    mutateReverse(list, partitionSize2, list.size - 1)
}

fun main() {
    println("Enter 2 partition sizes of your array:")

    val partitionSize1: Int = readLine()?.toInt() ?: throw IllegalArgumentException("Expected a number")
    val partitionSize2: Int = readLine()?.toInt() ?: throw IllegalArgumentException("Expected a number")

    println("Enter your array with spaces separating elements")

    val inputArr: MutableList<String>? = readLine()?.split(" ")?.toMutableList()

    if (inputArr == null) {
        print("Empty input")
        return
    }

    mutateSwapListPartitions(inputArr, partitionSize1, partitionSize2)

    println("Updated array:")

    inputArr.forEach { print("$it ") }
}
