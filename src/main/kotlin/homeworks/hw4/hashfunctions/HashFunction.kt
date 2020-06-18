package homeworks.hw4.hashfunctions

interface HashFunction<T> {
    val name: String
    fun apply(hashable: T): Int
}
