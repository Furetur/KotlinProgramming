package homeworks.hw6.benchmarks

class BenchmarkRunner(
    private val smallTestSize: Int,
    private val bigTestSize: Int,
    private val benchmarks: List<Benchmark>
) {

    interface Benchmark {
        val name: String
        fun test(size: Int): BenchmarkResult
    }

    data class BenchmarkResult(
        val benchmarkName: String,
        val size: Int,
        val quickSortTime: Long,
        val asyncQuickSortTime: Long
    )

    fun runBenchmarks(): List<BenchmarkResult> {
        return benchmarks.map { it.test(smallTestSize) } + benchmarks.map { it.test(bigTestSize) }
    }
}
