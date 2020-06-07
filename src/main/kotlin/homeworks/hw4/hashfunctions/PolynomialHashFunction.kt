package homeworks.hw4.hashfunctions

class PolynomialHashFunction(private val prime: Int, private val modulo: Int) : HashFunction<String> {

    companion object {
        const val name = "P"
    }

    override val name: String = "Polynomial Hash Function"

    override fun apply(hashable: String): Int {
        var primeRaisedToTheCurrentPower = 1
        var hash = 0

        for (character in hashable) {
            hash += (character.toInt() * primeRaisedToTheCurrentPower) % modulo
            primeRaisedToTheCurrentPower = (primeRaisedToTheCurrentPower * prime) % modulo
        }
        return hash % modulo
    }
}
