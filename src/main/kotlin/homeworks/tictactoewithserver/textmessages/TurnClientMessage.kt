package homeworks.tictactoewithserver.textmessages

class TurnClientMessage(command: String) : TextMessage(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "turn"
        const val requiredArgumentsNumber = 1

        fun compose(position: Int): String {
            return "turn $position"
        }
    }

    val position = mustBePosition(arguments[0])
}
