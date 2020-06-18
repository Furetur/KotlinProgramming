package homeworks.hw6.benchmarks

class ReversedListBenchmark : QuickSortBenchmark<Int>() {
    override val name = "Reversed List"

    override fun getTestList(size: Int): MutableList<Int> {
        return MutableList(size) { size - it }
    }
}
