package homeworks.tictactoewithserver.tictactoe.controllers

import homeworks.tictactoewithserver.tictactoe.models.RemoteGameModel
import io.ktor.util.KtorExperimentalAPI
import tornadofx.Controller
import tornadofx.stringBinding
import tornadofx.getValue

@KtorExperimentalAPI
class LobbyController : Controller() {
    val model = RemoteGameModel()

    val canJoinGameProperty = model.gameStartedProperty
    val canJoinGame by canJoinGameProperty

    val statusText = stringBinding(model.connectedProperty, model.gameStartedProperty, model.errorMessageProperty) {
        when {
            model.errorMessage != null -> model.errorMessage
            model.gameStarted -> "Game Started"
            model.connected -> "Waiting for other players"
            else -> "Connecting"
        }
    }

    fun connect() {
        model.connect()
    }

    fun disconnect() {
        model.disconnect()
    }
}
