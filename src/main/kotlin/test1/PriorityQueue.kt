package test1

import java.lang.IllegalStateException
import java.util.*
import java.util.PriorityQueue
import kotlin.Comparator


/**
 * Priority queue
 * @param E type of elements in the queue
 * @constructor creates an empty priority queue
 */
class PriorityQueue<E> {
    private val entryComparator = Comparator<Entry<E>> { entry1, entry2 -> entry1.priority - entry2.priority }

    private val treeSet: TreeSet<Entry<E>> = TreeSet(entryComparator)

    /**
     * Number of enqueued elements
     */
    val size: Int
        get() = treeSet.size


    private class Entry<E>(val value: E, val priority: Int)


    /**
     * Add element to the queue
     * @param value of the element
     * @param priority of the element
     */
    fun enqueue(value: E, priority: Int) {
        val newEntry = Entry(value, priority)
        treeSet.add(newEntry)
    }


    /**
     * Remove element with the highest priority from the queue
     * @return element with the highest priority
     * @throws IllegalStateException if the queue is empty
     */
    fun dequeue(): E {
        return treeSet.pollLast()?.value ?: throw IllegalStateException("Tried to dequeue from the empty queue")
    }
}

