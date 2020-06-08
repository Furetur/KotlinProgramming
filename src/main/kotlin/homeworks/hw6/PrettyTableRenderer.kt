package homeworks.hw6

class PrettyTableRenderer(private val columnsNumber: Int, private val columnWidth: Int) {

    companion object {
        const val emptyCellPlaceholder = "*****"
    }

    private val rows = mutableListOf<Row>()

    data class Row(val cells: List<String>)

    fun addRow(rowName: String, vararg cells: String) {
        addRow(rowName, cells.toList())
    }

    fun addRow(rowName: String, cells: List<String>) {
        addRow(listOf(rowName) + cells)
    }

    fun addRow(cells: List<String>) {
        rows.add(Row(cells))
    }

    fun renderTable(): String {
        return rows.map { renderRow(it) }.joinToString("\n")
    }

    private fun renderRow(row: Row): String {
        return (0 until columnsNumber)
            .map { if (row.cells.indices.contains(it)) row.cells[it] else emptyCellPlaceholder }
            .joinToString("|") { renderCell(it) }
    }

    private fun renderCell(str: String): String {
        return if (str.length > columnWidth) {
            str.toUpperCase().substring(0 until columnWidth)
        } else {
            str.toUpperCase() + " ".repeat(columnWidth - str.length)
        }
    }
}
