package test1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.fail
import java.lang.IllegalStateException
import kotlin.math.abs


class Container<E>(val value: E)


internal class PriorityQueueTest {
    var queue = PriorityQueue<Int>()


    @BeforeEach
    fun resetQueue() {
        queue = PriorityQueue<Int>()
    }


    @Test
    fun getSize_shouldReturnZeroForNewQueue() {
        assertEquals(0, queue.size)
    }


    @Test
    fun getSize_shouldReturnNumberOfEnqueuedElements() {
        for (i in 1..10) {
            queue.enqueue(i, i)
        }
        assertEquals(10, queue.size)
    }


    @Test
    fun getSize_shouldIncrementAfterEnqueue() {
        for (i in 1..10) {
            queue.enqueue(i, i)
        }
        val oldSize = queue.size
        queue.enqueue(15, 15)
        assertEquals(oldSize + 1, queue.size)
    }


    @Test
    fun getSize_shouldDecrementAfterDequeue() {
        for (i in 1..10) {
            queue.enqueue(i, i)
        }
        val oldSize = queue.size
        queue.dequeue()
        assertEquals(oldSize - 1, queue.size)
    }


    @Test
    fun enqueue_shouldWorkForEmptyQueue() {
        queue.enqueue(190, 1)
        val valueFromQueue = queue.dequeue()
        assertEquals(190, valueFromQueue)
    }


    @Test
    fun enqueue_shouldEnqueueElementsWithoutExceptions() {
        for (i in 0..1000) {
            queue.enqueue(i, i)
        }
    }


    @Test
    fun dequeue_shouldThrowExceptionForEmptyQueue() {
        try {
            queue.dequeue()
            fail("Expected an IllegalStateException to be thrown")
        } catch (e: IllegalStateException) {
            // success
        }
    }


    @Test
    fun dequeue_shouldThrowExceptionForEmptiedQueue() {
        for (i in 0..100) {
            queue.enqueue(i, i)
        }
        for (i in 0..100) {
            queue.dequeue()
        }
        try {
            queue.dequeue()
            fail("Expected an IllegalStateException to be thrown")
        } catch (e: IllegalStateException) {
            // success
        }
    }


    @Test
    fun dequeue_shouldDeleteElement() {
        queue.enqueue(1, 1)
        queue.dequeue()
        assertEquals(0, queue.size)
    }


    @Test
    fun dequeue_shouldBeAbleToDequeueAllElements() {
        for (i in 0..100) {
            queue.enqueue(i, i)
        }
        for (i in 0..100) {
            queue.dequeue()
        }
        assertEquals(0, queue.size)
    }


    @Test
    fun dequeue_shouldGetElementWithTopPriority() {
        queue.enqueue(0, 2)
        queue.enqueue(101, 1)
        assertEquals(0, queue.dequeue())
    }


    @Test
    fun dequeue_shouldGetElementsInTheCorrectOrder() {
        val expectedOutput = MutableList<Int>(100) { it }

        for (i in 99 downTo 0) {
            queue.enqueue(i, 99 - i)
        }

        val actualOutput = mutableListOf<Int>()

        for (i in 0..99) {
            actualOutput.add(queue.dequeue())
        }
        assertEquals(expectedOutput, actualOutput)
    }


    @Test
    fun dequeue_shouldGetElementsInTheCorrectOrderBig() {
        val expectedOutput = MutableList<Int>(1000) { it }

        for (i in 999 downTo 0) {
            queue.enqueue(i, 9999 - i)
        }

        val actualOutput = mutableListOf<Int>()

        for (i in 0..999) {
            actualOutput.add(queue.dequeue())
        }
        assertEquals(expectedOutput, actualOutput)
    }


    @Test
    fun dequeue_shouldReturnValueWithHighestPriorityInPopulatedQueue() {
        for (i in 0..1000) {
            queue.enqueue(i, i)
        }
        queue.enqueue(-1, 100000)
        assertEquals(-1, queue.dequeue())
    }


    // custom datatypes

    @Test
    fun enqueue_shouldEnqueueCustomTypes() {
        val abstractQueue = PriorityQueue<Container<Int>>()
        val value = Container<Int>(1)

        abstractQueue.enqueue(value, 0)
        assertEquals(value, abstractQueue.dequeue())
    }


    @Test
    fun dequeue_shouldGetCustomTypesInCorrectOrder() {
        val abstractQueue = PriorityQueue<Container<Int>>()
        val expectedOutput = MutableList<Container<Int>>(100) {Container(it)}
        for (i in 99 downTo 0) {
            abstractQueue.enqueue(expectedOutput[i], 100 - i)
        }
        val actualOutput = mutableListOf<Container<Int>>()
        for (i in 0..99) {
            actualOutput.add(abstractQueue.dequeue())
        }
        assertEquals(expectedOutput, actualOutput)
    }

}