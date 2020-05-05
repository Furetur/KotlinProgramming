package homeworks.hw6

const val COLUMN_WIDTH = 50
const val COLUMNS_NUMBER = 3
const val SMALL_TEST_SIZE = 1000
const val BIG_TEST_SIZE = 10000

fun getColumn(str: String): String {
    return str.toUpperCase() + " ".repeat(COLUMN_WIDTH - str.length)
}

fun printTestResults(testName: String, results: Pair<Long, Long>) {
    val (time1, time2) = results
    val firstColumn = getColumn(testName)
    val secondColumn = getColumn(time1.toString())
    val thirdColumn = getColumn(time2.toString())
    println("$firstColumn|$secondColumn|$thirdColumn")
}

fun main() {
    println("${getColumn("test")}|${getColumn("quicksort (ms)")}|${getColumn("async quicksort (ms)")}")
    println("-".repeat(COLUMN_WIDTH * COLUMNS_NUMBER))
    printTestResults("reversed list, size=1000", measureOnReversedList(SMALL_TEST_SIZE))
    printTestResults("reversed list, size=10000", measureOnReversedList(BIG_TEST_SIZE))
    printTestResults("shuffled list, size=1000", measureOnShuffledList(SMALL_TEST_SIZE))
    printTestResults("shuffled list, size=10000", measureOnShuffledList(BIG_TEST_SIZE))
    printTestResults("string list, size=1000", measureOnStringList(SMALL_TEST_SIZE))
    printTestResults("string list, size=10000", measureOnStringList(BIG_TEST_SIZE))
}
