package tests.pairsgame

import tornadofx.launch

fun main() {
    println("Please specify an even size of the field")
    val fieldLinearSizeString = readLine()
    val fieldLinearSize = fieldLinearSizeString?.toIntOrNull()
    if (fieldLinearSize == null || fieldLinearSize % 2 != 0) {
        println("The size should be an even integer")
        return
    }
    launch<PairsGameApp>(arrayOf(fieldLinearSizeString))
}
