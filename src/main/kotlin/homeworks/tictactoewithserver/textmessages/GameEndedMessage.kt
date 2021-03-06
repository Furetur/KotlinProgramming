package homeworks.tictactoewithserver.textmessages

class GameEndedMessage(command: String) : TextMessage(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "gameEnded"
        const val requiredArgumentsNumber = 1

        fun compose(playerId: Int): String {
            return "gameEnded $playerId"
        }
    }

    val winner = mustBePlayerIdOrNegativeOne(arguments[0])
}
