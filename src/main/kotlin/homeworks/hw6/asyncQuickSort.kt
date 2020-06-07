package homeworks.hw6

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun <E : Comparable<E>> asyncQuicksort(list: MutableList<E>, left: Int = 0, right: Int = list.size) {
    coroutineScope {
        if (list.isEmpty() || left >= right) {
            return@coroutineScope
        }
        val currentSubList = list.subList(left, right)
        val newPivotIndex = partition(currentSubList)
        launch {
            asyncQuicksort(currentSubList, 0, newPivotIndex)
        }
        launch {
            asyncQuicksort(currentSubList, newPivotIndex + 1)
        }
    }
}
