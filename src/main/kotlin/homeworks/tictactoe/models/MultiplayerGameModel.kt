package homeworks.tictactoe.models

import homeworks.tictactoe.GameClient
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class MultiplayerGameModel(private val client: GameClient) : GameWithFriendModel() {
    init {
        client.onTurn = { playerId, position ->
            if (playerId != client.playerId && activePlayer == playerId) {
                makeTurn(position)
            }
        }
        client.onOtherPlayerDisconnected = {
            client.playerId?.let {
                winner = it
            }
            gameOver = true
        }
        client.onDisconnect = {
            if (!gameOver) {
                errorMessage = "Lost connection with the server"
            }
        }
        gameOverProperty.addListener { _, _, newValue ->
            if (newValue == true) {
                client.close()
            }
        }
    }

    override fun startTurn() {
        waiting = activePlayer != client.playerId
    }

    @KtorExperimentalAPI
    override fun commitTurn(position: Int) {
        super.commitTurn(position)
        client.makeTurn(position)
    }
}
