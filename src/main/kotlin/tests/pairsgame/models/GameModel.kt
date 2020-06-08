package tests.pairsgame.models

import tests.pairsgame.PairsGameApp.Companion.MAX_NUMBER_OF_BUTTONS_PICKED
import tornadofx.asObservable
import tornadofx.booleanBinding
import java.lang.IllegalArgumentException

class GameModel(size: Int) {
    val field: List<Int>

    val pickedButtons = mutableListOf<Int>().asObservable()

    val revealedButtons = mutableListOf<Int>().asObservable()

    val gameOver = booleanBinding(this, revealedButtons) {
        revealedButtons.size == field.size
    }

    init {
        if (size <= 0 || size % 2 != 0) {
            throw IllegalArgumentException("Size should be an even integer")
        }
        val values = List(size / 2) { it }
        field = (values + values).shuffled()
    }

    fun pickButton(position: Int) {
        // if this button is already picked
        if (pickedButtons.contains(position)) {
            return
        }
        // if the position is not valid
        if (!field.indices.contains(position)) {
            throw IllegalArgumentException("Button with this position does not exist")
        }
        // if it is a new series of picked buttons
        if (pickedButtons.size == MAX_NUMBER_OF_BUTTONS_PICKED) {
            pickedButtons.clear()
        }
        pickedButtons.add(position)
        checkCorrectCombination()
    }

    private fun checkCorrectCombination() {
        if (pickedButtons.size == MAX_NUMBER_OF_BUTTONS_PICKED) {
            if (isCurrentCombinationCorrect()) {
                revealedButtons.addAll(pickedButtons)
                pickedButtons.clear()
            }
        }
    }

    private fun isCurrentCombinationCorrect(): Boolean {
        return if (pickedButtons.isEmpty()) {
            false
        } else {
            val pickedButtonsValues = pickedButtons.map { field[it] }
            return pickedButtonsValues.all { it == pickedButtonsValues.first() }
        }
    }
}
