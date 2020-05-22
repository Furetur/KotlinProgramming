package homeworks.tictactoe.models

import homeworks.tictactoe.bots.Bot
import homeworks.tictactoe.bots.EasyBot
import homeworks.tictactoe.bots.HardBot

class GameWithBotModel(playerId: Int, private val botId: Int, botType: String) : GameWithFriendModel() {
    private val bot: Bot = when (botType) {
        "easy" -> EasyBot(field, playerId, botId)
        "hard" -> HardBot(field, playerId, botId)
        else -> EasyBot(field, playerId, botId)
    }

    override fun startTurn() {
        if (activePlayer == botId) {
            waiting = true
            makeTurn(bot.makeTurn())
        } else {
            waiting = false
        }
    }
}
