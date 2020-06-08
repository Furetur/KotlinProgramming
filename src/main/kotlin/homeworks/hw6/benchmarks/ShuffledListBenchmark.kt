package homeworks.hw6.benchmarks

class ShuffledListBenchmark : QuickSortBenchmark<Int>() {
    override val name: String = "Shuffled List"

    override fun getTestList(size: Int): MutableList<Int> {
        val list = MutableList(size) { it }
        list.shuffle()
        return list
    }
}
