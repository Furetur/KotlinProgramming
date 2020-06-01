package homeworks.tictactoe.views

import homeworks.tictactoe.controllers.GameController
import homeworks.tictactoe.controllers.LobbyController
import homeworks.tictactoe.models.MultiplayerGameModel
import io.ktor.util.KtorExperimentalAPI
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tornadofx.View
import tornadofx.alert
import tornadofx.label
import tornadofx.vbox
import tornadofx.Scope
import tornadofx.find

@KtorExperimentalAPI
class LobbyView : View("Lobby") {
    private val controller: LobbyController by inject()
    override val root = vbox {
        label(controller.statusText)
    }

    override fun onDock() {
        super.onDock()
        controller.onGameStart {
            startGame()
        }
        controller.onConnectionError {
            controller.close()
            showErrorMessage("Error Connecting")
        }
        controller.start()
    }

    private fun showErrorMessage(text: String) {
        alert(Alert.AlertType.INFORMATION, text, "Return to the main menu", ButtonType.OK, actionFn = {
            replaceWith<MainMenuView>()
        })
    }

    private fun startGame() {
        val gameScope = Scope()
        val model = MultiplayerGameModel(controller.client)
        val game = GameController(model)
        setInScope(game, gameScope)
        replaceWith(find(GameWithFriendView::class, gameScope))
    }
}
