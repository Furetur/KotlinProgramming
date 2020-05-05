package homeworks.hw6

internal class QuickSortKtTest : SortTest() {
    override fun <E : Comparable<E>> sort(list: MutableList<E>) {
        quicksort(list)
    }
}
