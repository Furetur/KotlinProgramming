package homeworks.hw6

import homeworks.hw6.benchmarks.BenchmarkRunner
import homeworks.hw6.benchmarks.ReversedListBenchmark
import homeworks.hw6.benchmarks.ShuffledListBenchmark
import homeworks.hw6.benchmarks.StringBenchmark

const val COLUMN_WIDTH = 50
const val COLUMNS_NUMBER = 3
const val SMALL_TEST_SIZE = 1000
const val BIG_TEST_SIZE = 10000

val benchmarks = listOf(ReversedListBenchmark(), ShuffledListBenchmark(), StringBenchmark())

val benchmarkRunner = BenchmarkRunner(SMALL_TEST_SIZE, BIG_TEST_SIZE, benchmarks)

val tableRenderer = PrettyTableRenderer(COLUMNS_NUMBER, COLUMN_WIDTH)

fun main() {
    val results = benchmarkRunner.runBenchmarks()

    tableRenderer.addRow("Test", "QuickSort (ms)", "Async QuickSort (ms)")

    for (result in results) {
        tableRenderer.addRow(
            "${result.benchmarkName}, SIZE=${result.size}",
            result.quickSortTime.toString(),
            result.asyncQuickSortTime.toString()
        )
    }

    println(tableRenderer.renderTable())
}
