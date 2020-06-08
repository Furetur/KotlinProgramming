package homeworks.tictactoewithserver.tictactoe.stylesheets

import TicTacToeApp.Companion.APP_WIDTH
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.em
import tornadofx.px
import tornadofx.box
import tornadofx.c

class MainStylesheet : Stylesheet() {

    companion object {
        val ticTacToeTitle by cssclass()
        val mainMenuButton by cssclass()

        val gameFieldButton by cssclass()
        val activePlayerLabel by cssclass()

        val gameSettingsBody by cssclass()
        val gameSettingsButton by cssclass()

        const val FONT_SIZE_MEDIUM = 1.5
        const val FONT_SIZE_BIG = 3

        const val BUTTON_WIDTH = 100
        const val BUTTON_HEIGHT = BUTTON_WIDTH
    }

    init {
        ticTacToeTitle {
            fontSize = FONT_SIZE_BIG.em
        }
        mainMenuButton {
            minWidth = APP_WIDTH.px
        }
        activePlayerLabel {
            fontSize = FONT_SIZE_MEDIUM.em
        }
        gameFieldButton {
            minWidth = BUTTON_WIDTH.px
            minHeight = BUTTON_HEIGHT.px
            fontWeight = FontWeight.EXTRA_LIGHT
            fontSize = FONT_SIZE_BIG.em
            borderColor += box(
                top = Color.GREY,
                right = Color.GREY,
                bottom = Color.GREY,
                left = Color.GREY
            )
            backgroundColor += c("white")
        }
        gameSettingsBody {
            fontSize = FONT_SIZE_MEDIUM.em
        }
        gameSettingsButton {
            minWidth = (APP_WIDTH / 2).px
        }
    }
}
