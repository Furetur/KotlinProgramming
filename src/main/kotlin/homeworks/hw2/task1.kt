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
    val regexPattern = pattern.repeat(repeatsAtLeast - 1) + "($pattern)+"
    val repeatingPatterRegex = Regex(regexPattern)
    return repeatingPatterRegex.findAll(targetString)
}

private fun howManyIllegalRepetitions(pattern: String, bannedRepetitionsNumber: Int, repeatingPattern: String): Int {
    val repetitions = repeatingPattern.length / pattern.length
    return if (repetitions >= bannedRepetitionsNumber) {
        repetitions - bannedRepetitionsNumber + 1
    } else {
        0
    }
}
fun howManyCharactersNeedToBeRemoved(bannedPattern: String, bannedRepetitionsNumber: Int, targetStr: String): Int {
    return findAllRepeatingPatterns(bannedPattern, bannedRepetitionsNumber, targetStr)
        .map { match -> howManyIllegalRepetitions(bannedPattern, bannedRepetitionsNumber, match.value) }
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

    val charactersToBeRemovedCount = howManyCharactersNeedToBeRemoved(
        "x",
        BANNED_X_REPETITIONS_NUMBER,
        inputString
    )

    println("$charactersToBeRemovedCount character(s) need to be removed")
    return
}
