package tests.pairsgame.controllers

import tests.pairsgame.PairsGameApp.Companion.FIELD_SIZE
import tests.pairsgame.models.GameModel
import tornadofx.Controller
import tornadofx.booleanBinding
import tornadofx.onChange
import tornadofx.stringBinding

class GameController : Controller() {
    val model = GameModel(FIELD_SIZE)
    val buttonStatuses = model.field.withIndex().map { ButtonStatus(it.index, it.value) }

    inner class ButtonStatus(val position: Int, private val value: Int) {
        private val isPicked = booleanBinding(model.pickedButtons) {
            contains(position)
        }
        private val isRevealed = booleanBinding(model.revealedButtons) {
            contains(position)
        }
        val isShown = booleanBinding(isPicked, isRevealed) {
            isPicked.value || isRevealed.value
        }
        val text = stringBinding(this, isShown) {
            if (isShown.value) {
                value.toString()
            } else {
                ""
            }
        }
    }

    fun pickButton(button: ButtonStatus) {
        model.pickButton(button.position)
    }

    fun onVictory(listener: () -> Unit) {
        model.gameOver.onChange { gameOver ->
            if (gameOver) {
                listener()
            }
        }
    }
}
