package homeworks.hw2

import java.lang.IllegalArgumentException

const val BANNED_X_REPETITIONS_NUMBER = 3

fun findAllRepeatingPatterns(pattern: String, repeatsAtLeast: Int, targetString: String): Sequence<MatchResult> {
    if (repeatsAtLeast < 1) {
        throw IllegalArgumentException("repeatsAtLeast should be greater than 0")
    }
    if (pattern.isEmpty()) {
        throw IllegalArgumentException("Pattern cannot be empty")
    }
    val repeatingPatterRegex = Regex("($pattern){$repeatsAtLeast,}")
    return repeatingPatterRegex.findAll(targetString)
}

private fun countIllegalRepetitions(pattern: String, bannedRepetitionsNumber: Int, repeatingPattern: String): Int {
    val repetitions = repeatingPattern.length / pattern.length
    return if (repetitions >= bannedRepetitionsNumber) {
        repetitions - bannedRepetitionsNumber + 1
    } else {
        0
    }
}

fun countIllegalCharacters(bannedPattern: String, bannedRepetitionsNumber: Int, targetStr: String): Int {
    return findAllRepeatingPatterns(bannedPattern, bannedRepetitionsNumber, targetStr)
        .map { match -> countIllegalRepetitions(bannedPattern, bannedRepetitionsNumber, match.value) }
        .map { illegalRepetitionsNumber -> illegalRepetitionsNumber * bannedPattern.length }
        .sum()
}

fun main() {
    println("Enter your string and we will determine how many characters need to be removed to delete that naughty XXX")
    val inputString = readLine()

    if (inputString == null) {
        println("A string should be provided")
        return
    }

    val charactersToBeRemovedCount = countIllegalCharacters(
        "x",
        BANNED_X_REPETITIONS_NUMBER,
        inputString
    )

    println("$charactersToBeRemovedCount character(s) need to be removed")
    return
}
