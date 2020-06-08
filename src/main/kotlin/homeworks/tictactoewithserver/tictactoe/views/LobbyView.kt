package homeworks.tictactoewithserver.tictactoe.views

import homeworks.tictactoewithserver.tictactoe.controllers.GameController
import homeworks.tictactoewithserver.tictactoe.controllers.LobbyController
import io.ktor.util.KtorExperimentalAPI
import tornadofx.View
import tornadofx.button
import tornadofx.label
import tornadofx.vbox
import tornadofx.onChange
import tornadofx.action
import tornadofx.Scope
import tornadofx.find

@KtorExperimentalAPI
class LobbyView : View("Lobby") {
    private val controller: LobbyController by inject()
    override val root = vbox {
        label(controller.statusText)
        button("Join") {
            isDisable = !controller.canJoinGame
            controller.canJoinGameProperty.onChange {
                isDisable = !it
            }
            action {
                startGame()
            }
        }
        button("Back") {
            action {
                controller.disconnect()
                replaceWith<MainMenuView>()
            }
        }
    }

    override fun onDock() {
        super.onDock()
        controller.connect()
    }

    private fun startGame() {
        val gameScope = Scope()
        val game = GameController(controller.model)
        setInScope(game, gameScope)
        replaceWith(find(GameWithFriendView::class, gameScope))
    }
}
