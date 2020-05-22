package homeworks.tictactoe.views

import homeworks.tictactoe.controllers.GameController
import javafx.scene.control.ToggleGroup
import homeworks.tictactoe.models.GameWithBotModel
import homeworks.tictactoe.stylesheets.MainStylesheet
import tornadofx.View
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.top
import tornadofx.label
import tornadofx.button
import tornadofx.action
import tornadofx.bottom
import tornadofx.vbox
import tornadofx.hbox
import tornadofx.radiobutton
import tornadofx.Scope
import tornadofx.find

class GameWithBotSettingsView : View("Game Settings") {
    private val playerGroup = ToggleGroup()
    private val botGroup = ToggleGroup()

    var selectedPlayerId = 0
    private val selectedBotId: Int
        get() = 1 - selectedPlayerId
    var selectedBotType = "easy"

    override val root = borderpane {
        top {
            vbox {
                addClass(MainStylesheet.gameSettingsBody)
                label("Choose your player")
                radiobutton("X", playerGroup) {
                    isSelected = true
                    action {
                        selectedPlayerId = 0
                    }
                }
                radiobutton("O", playerGroup) {
                    action {
                        selectedPlayerId = 1
                    }
                }
                label("Choose difficulty")
                radiobutton("Easy", botGroup) {
                    isSelected = true
                    action {
                        selectedBotType = "easy"
                    }
                }
                radiobutton("Hard", botGroup) {
                    action {
                        selectedBotType = "hard"
                    }
                }
            }
        }
        bottom {
            hbox {
                button("Back") {
                    addClass(MainStylesheet.gameSettingsButton)
                    action {
                        replaceWith<MainMenuView>()
                    }
                }

                button("Play") {
                    addClass(MainStylesheet.gameSettingsButton)
                    action {
                        startGame()
                    }
                }
            }
        }
    }

    private fun startGame() {
        val gameScope = Scope()
        val model = GameWithBotModel(selectedPlayerId, selectedBotId, selectedBotType)
        val game = GameController(model)
        setInScope(game, gameScope)
        replaceWith(find(GameWithFriendView::class, gameScope))
    }
}
