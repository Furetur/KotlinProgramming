package homeworks.hw6

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun <E : Comparable<E>> asyncQuicksort(list: MutableList<E>) {
    coroutineScope {
        if (list.isEmpty()) {
            return@coroutineScope
        }

        val newPivotIndex = partition(list)
        if (newPivotIndex >= 1) {
            val leftPart: MutableList<E> = list.subList(0, newPivotIndex)
            launch {
                asyncQuicksort(leftPart)
            }
        }
        if (newPivotIndex < list.lastIndex) {
            launch {
                asyncQuicksort(list.subList(newPivotIndex + 1, list.lastIndex + 1))
            }
        }
    }
}
