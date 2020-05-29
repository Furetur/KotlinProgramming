package tests.pairsgame.views

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import tests.pairsgame.controllers.GameController
import tests.pairsgame.stylesheets.MainStylesheet.Companion.gameTitle
import tornadofx.View
import tornadofx.borderpane
import tornadofx.top
import tornadofx.label
import tornadofx.alert
import tornadofx.addClass

class GameView : View("Find Pairs Game") {
    private val controller: GameController by inject()

    override val root = borderpane {
        top {
            label("Find All The Pairs") {
                addClass(gameTitle)
            }
        }
        bottom(GameFieldView::class)
    }

    override fun onDock() {
        controller.onVictory {
            showEndGameMessage()
        }
    }

    private fun showEndGameMessage() {
        alert(Alert.AlertType.INFORMATION, "You won", "", ButtonType.OK)
    }
}
