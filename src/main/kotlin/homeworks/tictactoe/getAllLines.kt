package homeworks.tictactoe

private fun <E> getRows(field: List<E>, size: Int): List<List<E>> {
    return field.chunked(size)
}

private fun <E> getColumns(field: List<E>, size: Int): List<List<E>> {
    return IntRange(0, size - 1).map { i -> field.slice(i until field.size step size) }
}

private fun <E> getDiagonals(field: List<E>, size: Int): List<List<E>> {
    val rows = getRows(field, size)
    return listOf(
        (0 until size).map { i -> rows[i][i] },
        (0 until size).map { i -> rows[i][size - i - 1] }
    )
}

fun <E> getAllLines(field: List<E>, size: Int): List<List<E>> {
    return listOf(
        getRows(field, size),
        getColumns(field, size),
        getDiagonals(field, size)
    ).flatten()
}
