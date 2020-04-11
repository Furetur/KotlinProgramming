package homeworks.hw4

const val HASH_MODULO = 53

const val POLYNOMIAL_HASH_PRIME = 131

const val PEARSON_RESOLUTION = 256

val pearsonTable: List<Int> = (0 until PEARSON_RESOLUTION).toList().shuffled()

const val TRIVIAL_HASH_SHIFT_SIZE = 8

val pearsonHash = fun(str: String): Int {
    var hash = str.length % PEARSON_RESOLUTION
    for (character in str) {
        hash = pearsonTable[hash.xor(character.toInt())] % HASH_MODULO
    }
    return hash % HASH_MODULO
}

val polynomialHash = fun(str: String): Int {
    var primeRaisedToTheCurrentPower = 1
    var hash = 0

    for (character in str) {
        hash += (character.toInt() * primeRaisedToTheCurrentPower) % HASH_MODULO
        primeRaisedToTheCurrentPower = (primeRaisedToTheCurrentPower * POLYNOMIAL_HASH_PRIME) % HASH_MODULO
    }
    return hash % HASH_MODULO
}

val trivialHash = fun(str: String): Int {
    var hash = 0
    for (character in str) {
        hash = hash.shl(TRIVIAL_HASH_SHIFT_SIZE)
        hash += character.toByte().toInt()
    }
    return hash % HASH_MODULO
}

val supportedHashFunctions = listOf(pearsonHash, polynomialHash, trivialHash)
val supportedHashFunctionsNames = listOf("pearsonHash", "polynomialHash", "trivialHash")
