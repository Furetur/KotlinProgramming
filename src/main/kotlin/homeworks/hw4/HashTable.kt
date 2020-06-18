package homeworks.hw4

import homeworks.hw4.hashfunctions.HashFunction
import java.lang.IllegalArgumentException

class HashTable<K, V>(hashFunction: HashFunction<K>, private var initialNumberOfBuckets: Int = 51) : MutableMap<K, V> {
    companion object {
        const val loadFactorThreshold = .75
        const val expansionMultiplier = .5
    }

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

    inner class BucketsManager {
        private var buckets = Array(initialNumberOfBuckets) { mutableListOf<HashTableEntry<K, V>>() }

        val numberOfBuckets: Int
            get() = buckets.size

        val notEmptyBuckets = buckets.filter { it.size > 0 }

        fun getBucket(key: K): MutableList<HashTableEntry<K, V>> {
            val hash = hashFunction.apply(key) % numberOfBuckets
            return buckets[hash]
        }

        fun getEntry(key: K): HashTableEntry<K, V>? {
            val itemsWithThisHash = bucketsManager.getBucket(key)
            return itemsWithThisHash.find { item -> item.key == key }
        }

        fun clear() {
            for (bucket in buckets) {
                bucket.clear()
            }
        }

        fun expand() {
            val numberOfBucketsToAdd = (numberOfBuckets * expansionMultiplier).toInt()
            buckets = buckets.copyOf(numberOfBuckets + numberOfBucketsToAdd)
                .map { it ?: mutableListOf() }.toTypedArray()
            rehash()
        }
    }

    private val bucketsManager = BucketsManager()

    init {
        if (initialNumberOfBuckets < 2) {
            throw IllegalArgumentException("Number of buckets cannot be < 2")
        }
    }

    var hashFunction: HashFunction<K> = hashFunction
        set(function) {
            field = function
            rehash()
        }
    override val size: Int
        get() = entries.size
    override val entries = mutableSetOf<MutableMap.MutableEntry<K, V>>()
    override val keys: MutableSet<K>
        get() = entries.map { entry -> entry.key }.toMutableSet()
    override val values: MutableCollection<V>
        get() = entries.map { entry -> entry.value }.toMutableSet()
    val occupiedCellsCount: Int
        get() = bucketsManager.notEmptyBuckets.size
    val conflictsNumber: Int
        get() = bucketsManager.notEmptyBuckets.map { it.size - 1 }.sum()
    val maxConflictsForKey: Int
        get() = bucketsManager.notEmptyBuckets.map { it.size }.max() ?: 0

    val loadFactor: Float
        get() = size.toFloat() / bucketsManager.numberOfBuckets

    override fun containsKey(key: K): Boolean {
        val itemsWithThisHash = bucketsManager.getBucket(key)
        return itemsWithThisHash.any { item -> item.key == key }
    }

    override fun containsValue(value: V): Boolean {
        return entries.any { entry -> entry.value == value }
    }

    override fun get(key: K): V? {
        return bucketsManager.getEntry(key)?.value
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun clear() {
        bucketsManager.clear()
        entries.clear()
    }

    override fun put(key: K, value: V): V? {
        val itemWithThisKey = bucketsManager.getEntry(key)

        return if (itemWithThisKey == null) {
            val itemsWithThisHash = bucketsManager.getBucket(key)
            val newItem = HashTableEntry(key, value)
            itemsWithThisHash.add(newItem)
            entries.add(newItem)
            if (loadFactor >= loadFactorThreshold) {
                bucketsManager.expand()
            }
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
        val itemWithThisKey = bucketsManager.getEntry(key)
        return if (itemWithThisKey == null) {
            null
        } else {
            val itemsWithThisHash = bucketsManager.getBucket(key)
            itemsWithThisHash.remove(itemWithThisKey)
            entries.remove(itemWithThisKey)
            itemWithThisKey.value
        }
    }

    private fun rehash() {
        val savedEntries = entries.toSet()
        clear()
        for ((key, value) in savedEntries) {
            put(key, value)
        }
    }
}
