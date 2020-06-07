package homeworks.hw6

fun <E : Comparable<E>> quicksort(list: MutableList<E>, left: Int = 0, right: Int = list.size) {
    if (list.isEmpty() || left >= right) {
        return
    }
    val currentSubList = list.subList(left, right)
    val newPivotIndex = partition(currentSubList)
    quicksort(currentSubList, 0, newPivotIndex)
    quicksort(currentSubList, newPivotIndex + 1)
}
