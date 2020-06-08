package homeworks.tictactoewithserver.tictactoe.bots

import TicTacToeApp.Companion.FIELD_LINEAR_SIZE
import TicTacToeApp.Companion.FIELD_SIZE
import homeworks.tictactoewithserver.logic.FieldManager
import javafx.beans.property.SimpleIntegerProperty

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
        val fieldManager = FieldManager(rawField, FIELD_LINEAR_SIZE)
        val almostCapturedLineByBot = fieldManager.getAlmostCapturedLineBy(botId)
        return getCellForBotByIndex(almostCapturedLineByBot?.firstFreeCell?.index)
    }

    private fun findVictoriousCellForPlayer(): SimpleIntegerProperty? {
        val fieldManager = FieldManager(rawField, FIELD_LINEAR_SIZE)
        val almostCapturedLineByPlayer = fieldManager.getAlmostCapturedLineBy(playerId)
        return getCellForBotByIndex(almostCapturedLineByPlayer?.firstFreeCell?.index)
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
