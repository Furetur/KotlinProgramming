package homeworks.tictactoewithserver.tictactoe.bots

import TicTacToeApp.Companion.FIELD_LINEAR_SIZE
import TicTacToeApp.Companion.FIELD_SIZE
import javafx.beans.property.SimpleIntegerProperty
import homeworks.tictactoewithserver.logic.getAlmostCapturedLineBy

class HardBot(private val gameField: List<SimpleIntegerProperty>, playerId: Int, botId: Int) :
    EasyBot(gameField, playerId, botId) {

    private val rawField: List<Int>
        get() = gameField.map { it.value }

    override fun pickCell(): SimpleIntegerProperty? {
        val options = listOfNotNull(
            findVictoriousCellForBot(),
            findVictoriousCellForPlayer(),
            pickMiddle(),
            randomlyPickCell()
        )
        return if (options.isEmpty()) null else options.first()
    }

    private fun getCellForBotByIndex(cellIndex: Int?): SimpleIntegerProperty? {
        return if (cellIndex == null) null else gameField[cellIndex]
    }

    private fun findVictoriousCellForBot(): SimpleIntegerProperty? {
        val almostCapturedLineByBot = getAlmostCapturedLineBy(rawField, FIELD_LINEAR_SIZE, botId)
        val cellIndex = almostCapturedLineByBot?.find { it.value == -1 }?.index
        return getCellForBotByIndex(cellIndex)
    }

    private fun findVictoriousCellForPlayer(): SimpleIntegerProperty? {
        val almostCapturedLineByPlayer = getAlmostCapturedLineBy(rawField, FIELD_LINEAR_SIZE, playerId)
        val cellIndex = almostCapturedLineByPlayer?.find { it.value == -1 }?.index
        return getCellForBotByIndex(cellIndex)
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
