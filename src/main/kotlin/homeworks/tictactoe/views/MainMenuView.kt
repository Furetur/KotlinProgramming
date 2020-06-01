package homeworks.tictactoe.views

import homeworks.tictactoe.controllers.GameController
import homeworks.tictactoe.controllers.LobbyController
import homeworks.tictactoe.models.GameWithFriendModel
import homeworks.tictactoe.stylesheets.MainStylesheet
import io.ktor.util.KtorExperimentalAPI
import tornadofx.View
import tornadofx.addClass
import tornadofx.label
import tornadofx.button
import tornadofx.action
import tornadofx.vbox
import tornadofx.Scope
import tornadofx.find

class MainMenuView : View("Tic Tac Toe") {
    @KtorExperimentalAPI
    override val root = vbox {
        label("Tic Tac Toe") {
            addClass(MainStylesheet.ticTacToeTitle)
        }
        button("Play with a friend locally") {
            addClass(MainStylesheet.mainMenuButton)
            action {
                startLocalGameWithFriend()
            }
        }
        button("Play with AI") {
            addClass(MainStylesheet.mainMenuButton)
            action {
                startLocalGameWithAI()
            }
        }
        button("Multiplayer") {
            addClass(MainStylesheet.mainMenuButton)
            action {
                goToMultiplayerLobby()
            }
        }
    }

    private fun startLocalGameWithFriend() {
        val gameScope = Scope()
        val model = GameWithFriendModel()
        val controller = GameController(model)
        setInScope(controller, gameScope)
        replaceWith(find(GameWithFriendView::class, gameScope))
    }

    private fun startLocalGameWithAI() {
        replaceWith<GameWithBotSettingsView>()
    }

    @KtorExperimentalAPI
    private fun goToMultiplayerLobby() {
        val gameScope = Scope()
        val controller = LobbyController()
        setInScope(controller, gameScope)
        replaceWith(find(LobbyView::class, gameScope))
    }
}
