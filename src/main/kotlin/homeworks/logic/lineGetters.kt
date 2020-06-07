package homeworks.logic

private fun isLineCaptured(line: List<Int>): Boolean {
    return !line.contains(-1) && line.distinct().size == 1
}

private fun isLineAlmostCaptured(line: List<Int>): Boolean {
    val capturingPlayer = line.find { it != -1 }
    val capturedCellsByPlayerNum = line.count { it == capturingPlayer }
    return line.contains(-1) && capturedCellsByPlayerNum == line.size - 1
}

private fun getFirstPlayerInLine(line: List<Int>): Int? {
    return line.find { it != -1 }
}

private fun findLines(
    field: List<Int>,
    size: Int,
    predicate: (List<IndexedValue<Int>>) -> Boolean
): List<List<IndexedValue<Int>>> {
    val lines = getAllLines(field, size)
    return lines.filter { line -> predicate(line) }
}

private fun getAlmostCapturedLines(field: List<Int>, size: Int): List<List<IndexedValue<Int>>> {
    return findLines(field, size) { line -> isLineAlmostCaptured(line.map { it.value }) }
}

fun getFirstCapturedLine(field: List<Int>, size: Int): List<IndexedValue<Int>>? {
    return findLines(field, size) { line -> isLineCaptured(line.map { it.value }) }.firstOrNull()
}

fun getAlmostCapturedLineBy(field: List<Int>, size: Int, player: Int): List<IndexedValue<Int>>? {
    return getAlmostCapturedLines(field, size).find { line ->
        line.map { it.value }.contains(player)
    }
}

fun getWinner(field: List<Int>, size: Int): Int? {
    val victoriousLine = getFirstCapturedLine(field, size)?.map { it.value }
    return victoriousLine?.get(0)
}
