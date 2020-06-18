package homeworks.tictactoewithserver.textmessages

class GameStartedMessage(command: String) : TextMessage(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "gameStarted"
        const val requiredArgumentsNumber = 1

        fun compose(playerId: Int): String {
            return "gameStarted $playerId"
        }
    }

    val playerId = mustBePlayerId(arguments[0])
}
