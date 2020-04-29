package homeworks.hw4

import java.lang.IllegalArgumentException

class HashTable<K, V>(hashFunction: (K) -> Int, private val tableSize: Int = 51) : MutableMap<K, V> {

    class HashTableEntry<K, V>(override val key: K, value: V) : MutableMap.MutableEntry<K, V> {
        override val value: V
            get() = actualValue

        private var actualValue = value

        override fun setValue(newValue: V): V {
            val oldValue = value
            actualValue = newValue
            return oldValue
        }
    }

    private var table = Array(tableSize) { mutableListOf<HashTableEntry<K, V>>() }
    private var itemsCount = 0

    init {
        if (tableSize < 2) {
            throw IllegalArgumentException("Table size cannot be < 1")
        }
    }

    var hashFunction: (K) -> Int = hashFunction
        set(function) {
            field = function
            // rehash
            val entries = entries
            clear()
            for (entry in entries) {
                put(entry.key, entry.value)
            }
        }
    override val size: Int
        get() = itemsCount
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = table.filter { cell -> cell.size > 0 }.flatten().toMutableSet()
    override val keys: MutableSet<K>
        get() = entries.map { entry -> entry.key }.toMutableSet()
    override val values: MutableCollection<V>
        get() = entries.map { entry -> entry.value }.toMutableSet()
    val occupiedCellsCount: Int
        get() = table.filter { cell -> cell.size > 0 }.size
    val maxConflictsForKey: Int
        get() = table.map { cell -> cell.size }.max()!!

    val loadFactor: Float
        get() = occupiedCellsCount.toFloat() / tableSize

    private fun getCellByKey(key: K): MutableList<HashTableEntry<K, V>> {
        val hash = hashFunction(key) % tableSize
        return table[hash]
    }

    override fun containsKey(key: K): Boolean {
        val itemsWithThisHash = getCellByKey(key)
        return itemsWithThisHash.any { item -> item.key == key }
    }

    override fun containsValue(value: V): Boolean {
        return entries.any { entry -> entry.value == value }
    }

    private fun getEntry(key: K): HashTableEntry<K, V>? {
        val itemsWithThisHash = getCellByKey(key)
        return itemsWithThisHash.find { item -> item.key == key }
    }

    override fun get(key: K): V? {
        return getEntry(key)?.value
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun clear() {
        // empty table
        for (cell in table) {
            cell.clear()
        }
        itemsCount = 0
    }

    override fun put(key: K, value: V): V? {
        val itemWithThisKey = getEntry(key)

        return if (itemWithThisKey == null) {
            val itemsWithThisHash = getCellByKey(key)
            val newItem = HashTableEntry(key, value)
            itemsWithThisHash.add(newItem)
            itemsCount++
            null
        } else {
            itemWithThisKey.setValue(value)
        }
    }

    override fun putAll(from: Map<out K, V>) {
        for (entry in from.entries) {
            put(entry.key, entry.value)
        }
    }

    override fun remove(key: K): V? {
        val itemWithThisKey = getEntry(key)
        return if (itemWithThisKey == null) {
            null
        } else {
            val itemsWithThisHash = getCellByKey(key)
            itemsWithThisHash.remove(itemWithThisKey)
            itemsCount--
            itemWithThisKey.value
        }
    }
}
