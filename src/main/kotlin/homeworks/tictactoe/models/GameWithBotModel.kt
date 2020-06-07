package homeworks.tictactoe.models

import homeworks.tictactoe.bots.Bot
import homeworks.tictactoe.bots.EasyBot
import homeworks.tictactoe.bots.HardBot
import javafx.beans.property.SimpleIntegerProperty
import java.lang.IllegalArgumentException

class GameWithBotModel(playerId: Int, private val botId: Int, botType: String) : LocalGameModel() {
    private val bot: Bot = when (botType) {
        "easy" -> EasyBot(field, playerId, botId)
        "hard" -> HardBot(field, playerId, botId)
        else -> EasyBot(field, playerId, botId)
    }

    override fun onTurnStart(playerId: Int) {
        super.onTurnStart(playerId)
        if (activePlayer == botId) {
            waiting = true
            makeTurn(botId, bot.makeTurn())
        } else {
            waiting = false
        }
    }

    private fun makeTurn(playerId: Int, cell: SimpleIntegerProperty) {
        val position = field.indexOf(cell)
        if (position == -1) {
            throw IllegalArgumentException("This cell does not exist")
        }
        makeTurn(playerId, position)
    }
}
