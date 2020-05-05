package homeworks.hw6

fun <E : Comparable<E>> quicksort(list: MutableList<E>) {
    if (list.isEmpty()) {
        return
    }

    val newPivotIndex = partition(list)
    if (newPivotIndex >= 1) {
        quicksort(list.subList(0, newPivotIndex))
    }
    if (newPivotIndex < list.lastIndex) {
        quicksort(list.subList(newPivotIndex + 1, list.lastIndex + 1))
    }
}
