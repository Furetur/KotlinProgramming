package homeworks.hw6.benchmarks

import homeworks.hw6.asyncQuicksort
import homeworks.hw6.quicksort
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

abstract class QuickSortBenchmark<E : Comparable<E>> : BenchmarkRunner.Benchmark {
    abstract override val name: String
    abstract fun getTestList(size: Int): MutableList<E>

    override fun test(size: Int): BenchmarkRunner.BenchmarkResult {
        val testList1 = getTestList(size)
        val testList2 = testList1.toMutableList()
        val time1 = measureQuicksort(testList1)
        val time2 = measureAsyncQuicksort(testList2)
        return BenchmarkRunner.BenchmarkResult(name, size, time1, time2)
    }

    private fun measureQuicksort(list: MutableList<E>): Long {
        return measureTimeMillis {
            quicksort(list)
        }
    }

    private fun measureAsyncQuicksort(list: MutableList<E>): Long {
        return measureTimeMillis {
            runBlocking {
                asyncQuicksort(list)
            }
        }
    }
}
