package homeworks.hw6

import kotlinx.coroutines.runBlocking

internal class AsyncQuickSortKtTest : SortTest() {
    override fun <E : Comparable<E>> sort(list: MutableList<E>) {
        runBlocking {
            asyncQuicksort(list)
        }
    }
}
