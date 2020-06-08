package homeworks.tictactoewithserver.server

import homeworks.tictactoewithserver.logic.LocalGameLoop
import homeworks.tictactoewithserver.textmessages.GameEndedMessage
import homeworks.tictactoewithserver.textmessages.GameStartedMessage
import homeworks.tictactoewithserver.textmessages.TurnServerMessage

class ServerGameLoop(private val playersManager: GameServer.PlayersManager) : LocalGameLoop() {
    var gameOn = false

    override fun onGameStart() {
        println("Game started")
        super.onGameStart()
        gameOn = true
        playersManager.notifyEach { GameStartedMessage.compose(it) }
    }

    override fun onTurnStart(playerId: Int) {
        println("Turn for player #$playerId starts")
        super.onTurnStart(playerId)
    }

    override fun onTurnMade(playerId: Int, position: Int) {
        println("Turn made by #$playerId to position $position")
        super.onTurnMade(playerId, position)
        playersManager.notifyAll(TurnServerMessage.compose(playerId, position))
    }

    fun onPlayerDisconnected(playerId: Int) {
        println("Ending the game: player #$playerId disconnected")
        onVictory(1 - playerId)
    }

    override fun onVictory(playerId: Int) {
        println("Player #$playerId won")
        super.onVictory(playerId)
        gameOn = false
        playersManager.notifyAll(GameEndedMessage.compose(playerId))
        playersManager.kickAll()
    }

    override fun onTie() {
        println("Tie!")
        super.onTie()
        gameOn = false
        playersManager.notifyAll(GameEndedMessage.compose(-1))
        playersManager.kickAll()
    }
}
