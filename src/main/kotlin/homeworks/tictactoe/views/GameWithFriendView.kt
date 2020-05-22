package homeworks.tictactoe.views

import homeworks.tictactoe.controllers.GameController
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import homeworks.tictactoe.stylesheets.MainStylesheet
import tornadofx.View
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.stringBinding
import tornadofx.alert
import tornadofx.top
import tornadofx.label

class GameWithFriendView : View("Game") {
    private val controller: GameController by inject()

    private val currentTurnLabelText = stringBinding(controller.activePlayerProperty) {
        "${controller.getPlayerFigure(value).toUpperCase()}'s turn"
    }

    override val root = borderpane {
        top {
            label(currentTurnLabelText) {
                addClass(MainStylesheet.activePlayerLabel)
            }
        }
        bottom(GameFieldView::class)
    }

    override fun onDock() {
        controller.onTie {
            showEndGameMessage("It's a Tie")
        }
        controller.onVictory { player ->
            showEndGameMessage("${controller.getPlayerFigure(player).toUpperCase()}'s win")
        }
        controller.startGame()
    }

    private fun showEndGameMessage(text: String) {
        alert(Alert.AlertType.INFORMATION, text, "Press OK to return to the main menu", ButtonType.OK, actionFn = {
            replaceWith<MainMenuView>()
        })
    }
}
