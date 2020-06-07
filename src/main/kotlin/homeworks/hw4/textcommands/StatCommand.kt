package homeworks.hw4.textcommands

import homeworks.hw4.HashTable

class StatCommand(command: String) : TextCommand<String, String>(name, requiredArgumentsNumber, command) {
    companion object {
        const val name = "stat"
        const val requiredArgumentsNumber = 0
    }

    override fun apply(hashTable: HashTable<String, String>): String {
        return "Occupied Cells: ${hashTable.occupiedCellsCount}. Load factor: ${hashTable.loadFactor}. " +
                "Max conflicts in one cell: ${hashTable.maxConflictsForKey}. " +
                "Total number of conflicts: ${hashTable.conflictsNumber}"
    }
}
