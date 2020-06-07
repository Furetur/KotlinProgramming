package homeworks.hw6

import java.lang.IllegalArgumentException

fun <E : Comparable<E>> partition(list: MutableList<E>): Int {
    if (list.isEmpty()) {
        throw IllegalArgumentException("Received an empty list")
    }
    val pivot = list.last()
    var firstElementGreaterThanPivot = 0
    for ((index, element) in list.withIndex()) {
        if (element < pivot) {
            // swap list[firstElementGreaterThanPivot] and list[index]
            list[index] = list[firstElementGreaterThanPivot]
            list[firstElementGreaterThanPivot] = element
            firstElementGreaterThanPivot += 1
        }
    }
    // swap list[firstElementGreaterThanPivot] and list[list.lastIndex]
    val temp = list[firstElementGreaterThanPivot]
    list[firstElementGreaterThanPivot] = list.last()
    list[list.lastIndex] = temp
    return firstElementGreaterThanPivot
}
