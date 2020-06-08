package tests.pairsgame.stylesheets

import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.em
import tornadofx.px

class MainStylesheet : Stylesheet() {
    companion object {
        val gameButton by cssclass()
        val gameTitle by cssclass()

        const val HEADER_HEIGHT = 100

        const val BUTTON_WIDTH = 100
        const val BUTTON_HEIGHT = BUTTON_WIDTH

        const val FONT_SIZE_BIG = 2
    }

    init {
        gameTitle {
            fontSize = FONT_SIZE_BIG.em
        }
        gameButton {
            minWidth = BUTTON_WIDTH.px
            minHeight = BUTTON_HEIGHT.px
        }
    }
}
