package homeworks.hw4

fun repopulateHashTable(hashTable: HashTable<String, String>, size: Int) {
    hashTable.clear()
    for (i in 1..size) {
        hashTable.put("key$i", "val$i")
    }
}
