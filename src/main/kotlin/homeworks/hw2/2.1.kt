package homeworks.hw2

import java.lang.IllegalArgumentException

const val X_LENGTH = 3
val CONSECUTIVE_X_REGEX = Regex("x+")

fun matchAllConsecutiveX(str: String): Sequence<MatchResult> {
    return CONSECUTIVE_X_REGEX.findAll(str)
}

fun howManyCharactersNeedToBeRemoved(str: String): Int {
    var charactersToBeRemovedCount = 0

    val allConsecutiveX = matchAllConsecutiveX(str)

    for (match in allConsecutiveX) {
        val curLength = match.value.length
        if (curLength < X_LENGTH) {
            continue
        }
        charactersToBeRemovedCount += curLength - X_LENGTH + 1
    }

    return charactersToBeRemovedCount
}

fun main() {
    println("Enter your string and we will determine how many characters need to be removed to delete that naughty XXX")
    val inputString = readLine() ?: throw IllegalArgumentException("Wrong input")
    val charactersToBeRemovedCount = howManyCharactersNeedToBeRemoved(inputString)

    println("$charactersToBeRemovedCount character(s) need to be removed")
}
