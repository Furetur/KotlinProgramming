package homeworks.hw6

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun <T : Comparable<T>> measureQuicksort(list: MutableList<T>): Long {
    return measureTimeMillis {
        quicksort(list)
    }
}

fun <E : Comparable<E>> measureAsyncQuicksort(list: MutableList<E>): Long {
    return measureTimeMillis {
        runBlocking {
            asyncQuicksort(list)
        }
    }
}

fun <E : Comparable<E>> measureTime(list: MutableList<E>): Pair<Long, Long> {
    val list1 = list.toMutableList()
    val list2 = list.toMutableList()
    val time1 = measureQuicksort(list1)
    val time2 = measureAsyncQuicksort(list2)
    return Pair(time1, time2)
}

fun measureOnReversedList(size: Int): Pair<Long, Long> {
    val list = MutableList(size) { size - it }
    return measureTime(list)
}

fun measureOnShuffledList(size: Int): Pair<Long, Long> {
    val list = MutableList(size) { it }
    list.shuffle()
    return measureTime(list)
}

fun getRandomString(): String {
    val letters = mutableListOf<Char>(
        'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z'
    )
    letters.shuffle()
    return letters.joinToString("")
}

fun measureOnStringList(size: Int): Pair<Long, Long> {
    val list = MutableList(size) { getRandomString() }
    return measureTime(list)
}
