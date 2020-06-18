package homeworks.hw4.hashfunctions

class TrivialHashFunction(private val shiftSize: Int, private val modulo: Int) : HashFunction<String> {

    override val name = "Trivial Hash Function"

    override fun apply(hashable: String): Int {
        var hash = 0
        for (character in hashable) {
            hash = hash.shl(shiftSize)
            hash += character.toByte().toInt()
        }
        return hash % modulo
    }
}
