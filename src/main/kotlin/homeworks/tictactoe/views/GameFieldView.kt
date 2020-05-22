package homeworks.tictactoe.views

import TicTacToeApp.Companion.FIELD_SIZE
import homeworks.tictactoe.controllers.GameController
import homeworks.tictactoe.stylesheets.MainStylesheet
import tornadofx.View
import tornadofx.addClass
import tornadofx.flowpane
import tornadofx.button
import tornadofx.action
import tornadofx.onChange

class GameFieldView : View() {
    private val controller: GameController by inject()

    override val root = flowpane {
        for (i in 0 until FIELD_SIZE) {
            val curButton = controller.buttonsData[i]
            button(curButton.text) {
                addClass(MainStylesheet.gameFieldButton)
                action {
                    controller.makeTurn(i)
                }
                isDisable = curButton.isDisabled.value
                curButton.isDisabled.onChange {
                    isDisable = it
                }
            }
        }
    }
}
