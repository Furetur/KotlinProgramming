package test1

import java.lang.IllegalStateException
import java.util.*
import java.util.PriorityQueue
import kotlin.Comparator


class PriorityQueue<E> {
    private val entryComparator = Comparator<Entry<E>> { entry1, entry2 -> entry1.priority - entry2.priority }

    private val treeSet: TreeSet<Entry<E>> = TreeSet(entryComparator)

    val size: Int
        get() = treeSet.size


    class Entry<E>(val value: E, val priority: Int)


    fun enqueue(value: E, priority: Int) {
        val newEntry = Entry(value, priority)
        treeSet.add(newEntry)
    }


    fun dequeue(): E {
        return treeSet.pollLast()?.value ?: throw IllegalStateException("Tried to dequeue from the empty queue")
    }
}

