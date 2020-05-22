package tests.pairsgame.views

import tests.pairsgame.controllers.GameController
import tests.pairsgame.stylesheets.MainStylesheet.Companion.gameButton
import tornadofx.View
import tornadofx.addClass
import tornadofx.flowpane
import tornadofx.button
import tornadofx.action
import tornadofx.onChange

class GameFieldView : View() {
    private val controller: GameController by inject()
    override val root = flowpane {
        for (buttonStatus in controller.buttonStatuses) {
            button(buttonStatus.text) {
                addClass(gameButton)
                isDisable = buttonStatus.isShown.value
                buttonStatus.isShown.onChange {
                    isDisable = it
                }
                action {
                    controller.pickButton(buttonStatus)
                }
            }
        }
    }
}
