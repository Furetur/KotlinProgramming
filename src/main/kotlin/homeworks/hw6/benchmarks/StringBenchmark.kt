package homeworks.hw6.benchmarks

class StringBenchmark : QuickSortBenchmark<String>() {
    override val name = "Permutation Benchmark"

    private fun getRandomLettersPermutation(): String {
        val letters = ('a'..'z').toMutableList()
        letters.shuffle()
        return letters.joinToString("")
    }

    override fun getTestList(size: Int): MutableList<String> {
        return MutableList(size) { getRandomLettersPermutation() }
    }
}
