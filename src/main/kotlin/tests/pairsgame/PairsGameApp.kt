package tests.pairsgame

import javafx.stage.Stage
import tests.pairsgame.stylesheets.MainStylesheet
import tests.pairsgame.stylesheets.MainStylesheet.Companion.BUTTON_HEIGHT
import tests.pairsgame.stylesheets.MainStylesheet.Companion.BUTTON_WIDTH
import tests.pairsgame.stylesheets.MainStylesheet.Companion.HEADER_HEIGHT
import tests.pairsgame.views.GameView
import tornadofx.App

class PairsGameApp : App(GameView::class, MainStylesheet::class) {
    companion object {
        var FIELD_LINEAR_SIZE = -1
        var FIELD_SIZE = -1
        const val MAX_NUMBER_OF_BUTTONS_PICKED = 2
    }

    override fun start(stage: Stage) {
        val fieldLinearSizeParameter = parameters.raw[0].toInt()
        FIELD_LINEAR_SIZE = fieldLinearSizeParameter
        FIELD_SIZE = FIELD_LINEAR_SIZE * FIELD_LINEAR_SIZE

        super.start(stage)

        stage.width = (BUTTON_WIDTH * FIELD_LINEAR_SIZE).toDouble()
        stage.height = (BUTTON_HEIGHT * FIELD_LINEAR_SIZE + HEADER_HEIGHT).toDouble()
        stage.isResizable = false
    }
}
