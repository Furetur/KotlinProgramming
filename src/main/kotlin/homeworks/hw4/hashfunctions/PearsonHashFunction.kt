package homeworks.hw4.hashfunctions

import homeworks.hw4.TextCommandRunner.Companion.PEARSON_RESOLUTION

class PearsonHashFunction(private val resolution: Int, private val modulo: Int) : HashFunction<String> {

    override val name = "Pearson Hash Function"

    private val pearsonTable: List<Int> = (0 until PEARSON_RESOLUTION).toList().shuffled()

    override fun apply(hashable: String): Int {
        var hash = hashable.length % resolution
        for (character in hashable) {
            hash = pearsonTable[hash.xor(character.toInt())] % modulo
        }
        return hash % modulo
    }
}
