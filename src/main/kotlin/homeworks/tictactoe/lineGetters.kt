package homeworks.tictactoe

import javafx.beans.property.SimpleIntegerProperty

private fun isLineCaptured(line: List<Int>): Boolean {
    return !line.contains(-1) && line.distinct().size == 1
}

private fun isLineAlmostCaptured(line: List<Int>): Boolean {
    val capturingPlayer = line.find { it != -1 }
    val capturedCellsByPlayerNum = line.count { it == capturingPlayer }
    return line.contains(-1) && capturedCellsByPlayerNum == line.size - 1
}

private fun getFirstPlayerInLine(line: List<SimpleIntegerProperty>): Int? {
    return line.find { it.value != -1 }?.value
}

private fun findLines(
    field: List<SimpleIntegerProperty>,
    size: Int,
    predicate: (List<Int>) -> Boolean
): List<List<SimpleIntegerProperty>> {
    val lines = getAllLines(field, size)
    return lines.filter { line -> predicate(line.map { it.value }) }
}

private fun getAlmostCapturedLines(field: List<SimpleIntegerProperty>, size: Int): List<List<SimpleIntegerProperty>> {
    return findLines(field, size) { isLineAlmostCaptured(it) }
}

fun getFirstCapturedLine(field: List<SimpleIntegerProperty>, size: Int): List<SimpleIntegerProperty>? {
    return findLines(field, size) { isLineCaptured(it) }.firstOrNull()
}

fun getAlmostCapturedLineBy(field: List<SimpleIntegerProperty>, size: Int, player: Int): List<SimpleIntegerProperty>? {
    return getAlmostCapturedLines(field, size).find { getFirstPlayerInLine(it) == player }
}
