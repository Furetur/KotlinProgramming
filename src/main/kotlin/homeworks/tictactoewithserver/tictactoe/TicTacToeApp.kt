import javafx.stage.Stage
import homeworks.tictactoewithserver.tictactoe.stylesheets.MainStylesheet
import homeworks.tictactoewithserver.tictactoe.stylesheets.MainStylesheet.Companion.BUTTON_WIDTH
import tornadofx.App
import homeworks.tictactoewithserver.tictactoe.views.MainMenuView

class TicTacToeApp : App(MainMenuView::class, MainStylesheet::class) {

    companion object {
        const val FIELD_LINEAR_SIZE = 3
        const val FIELD_SIZE = FIELD_LINEAR_SIZE * FIELD_LINEAR_SIZE
        const val APP_HEIGHT = 400.0
        const val APP_WIDTH = (BUTTON_WIDTH * FIELD_LINEAR_SIZE).toDouble()
    }

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = APP_WIDTH
        stage.height = APP_HEIGHT
        stage.isResizable = false
    }
}
