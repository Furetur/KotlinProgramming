package homeworks.tictactoe.bots

import javafx.beans.property.SimpleIntegerProperty

interface Bot {
    fun makeTurn(): SimpleIntegerProperty
}
