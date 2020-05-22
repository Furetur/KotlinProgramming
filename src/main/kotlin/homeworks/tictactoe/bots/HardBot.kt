package homeworks.tictactoe.bots

import TicTacToeApp.Companion.FIELD_LINEAR_SIZE
import TicTacToeApp.Companion.FIELD_SIZE
import javafx.beans.property.SimpleIntegerProperty
import homeworks.tictactoe.getAlmostCapturedLineBy

class HardBot(field: List<SimpleIntegerProperty>, playerId: Int, botId: Int) : EasyBot(field, playerId, botId) {
    override fun pickCell(): SimpleIntegerProperty? {
        val options = listOfNotNull(
            findVictoriousCellForBot(),
            findVictoriousCellForPlayer(),
            pickMiddle(),
            randomlyPickCell()
        )
        return if (options.isEmpty()) null else options.first()
    }

    private fun findVictoriousCellForBot(): SimpleIntegerProperty? {
        val almostCapturedLineByBot = getAlmostCapturedLineBy(field, FIELD_LINEAR_SIZE, botId)
        return almostCapturedLineByBot?.find { it.value == -1 }
    }

    private fun findVictoriousCellForPlayer(): SimpleIntegerProperty? {
        val almostCapturedLineByPlayer = getAlmostCapturedLineBy(field, FIELD_LINEAR_SIZE, playerId)
        return almostCapturedLineByPlayer?.find { it.value == -1 }
    }

    private fun pickMiddle(): SimpleIntegerProperty? {
        val medianIndex = FIELD_SIZE / 2
        return if (field[medianIndex].value == -1) {
            field[medianIndex]
        } else {
            null
        }
    }
}
