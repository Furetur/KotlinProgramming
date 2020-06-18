package homeworks.tictactoewithserver.tictactoe.bots

import javafx.beans.property.SimpleIntegerProperty
import java.lang.IllegalStateException

open class EasyBot(protected val field: List<SimpleIntegerProperty>, val playerId: Int, val botId: Int) : Bot {
    override fun makeTurn(): SimpleIntegerProperty {
        return pickCell() ?: throw IllegalStateException("The bot cannot pick any of the cells")
    }

    open fun pickCell(): SimpleIntegerProperty? {
        return randomlyPickCell()
    }

    fun randomlyPickCell(): SimpleIntegerProperty? {
        val freeCells = field.filter { it.value == -1 }
        return if (freeCells.isEmpty()) null else freeCells.random()
    }
}
