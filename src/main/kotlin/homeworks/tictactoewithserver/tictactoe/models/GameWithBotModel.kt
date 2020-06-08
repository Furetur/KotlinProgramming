package homeworks.tictactoewithserver.tictactoe.models

import homeworks.tictactoewithserver.tictactoe.bots.Bot
import homeworks.tictactoewithserver.tictactoe.bots.EasyBot
import homeworks.tictactoewithserver.tictactoe.bots.HardBot
import javafx.beans.property.SimpleIntegerProperty

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
        assert(position != -1)
        makeTurn(playerId, position)
    }
}
