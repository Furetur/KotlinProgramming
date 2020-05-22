package homeworks.tictactoe.views

import homeworks.tictactoe.controllers.GameController
import homeworks.tictactoe.models.GameWithFriendModel
import homeworks.tictactoe.stylesheets.MainStylesheet
import tornadofx.View
import tornadofx.addClass
import tornadofx.label
import tornadofx.button
import tornadofx.action
import tornadofx.vbox
import tornadofx.Scope
import tornadofx.find

class MainMenuView : View("Tic Tac Toe") {
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
}
