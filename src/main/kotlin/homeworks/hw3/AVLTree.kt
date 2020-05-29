package homeworks.hw3

import java.lang.Integer.max
import java.util.Stack
import kotlin.Comparator

/**
 * AVLTree implementation of MutableMap
 * @param K type of keys
 * @param V type of values
 */
class AVLTree<K, V> : MutableMap<K, V> {

    companion object {
        const val BALANCE_FACTOR_RIGHT_SUBTREE_TOO_HIGH = 2
        const val BALANCE_FACTOR_LEFT_SUBTREE_TOO_HIGH = -2
    }

    /**
     * Constructor of the ImmutableAVLTree
     * @param comparator is used to compare keys
     * @constructor Creates an empty AVLTree if parameters root and rootSize are not received,
     * acts as a wrapper around [root] otherwise
     */
    constructor(comparator: Comparator<K>) : this(comparator, null)

    private constructor(comparator: Comparator<K>, root: Node<K, V>?) {
        this.comparator = comparator
        this.root = root
    }

    private val comparator: Comparator<K>
    private var root: Node<K, V>?

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = (root?.asSequence()?.toMutableSet<MutableMap.MutableEntry<K, V>>() ?: mutableSetOf())

    override val keys: MutableSet<K>
        get() = root?.asSequence()?.map { it.key }?.toMutableSet() ?: mutableSetOf()

    override val size: Int
        get() = root?.size ?: 0

    override val values: MutableCollection<V>
        get() = root?.asSequence()?.map { it.value }?.toMutableList() ?: mutableListOf()

    /**
     * Node of the ImmutableAVLTree, which is an entry of this tree
     * @param K type of the node's key
     * @param V type of the node's value
     * @param comparator that is used to compare keys
     * @constructor Creates a node and sets its left and right children
     */
    class Node<K, V>(
        override val key: K,
        override var value: V,
        private val comparator: Comparator<K>,
        val left: Node<K, V>? = null,
        val right: Node<K, V>? = null
    ) : Iterable<Node<K, V>>, MutableMap.MutableEntry<K, V> {

        private val children: List<Node<K, V>>
            get() = listOfNotNull(left, right)

        private val leftHeight: Int
            get() = left?.height ?: 0
        private val rightHeight: Int
            get() = right?.height ?: 0

        val size: Int = (left?.size ?: 0) + (right?.size ?: 0) + 1

        private val height: Int = max(leftHeight, rightHeight) + 1
        private val balanceFactor: Int = rightHeight - leftHeight

        private fun rotateRight(): Node<K, V> {
            // store variables for quick access
            val newRoot = left ?: throw IllegalCallerException("Node's left child cannot be null")
            val futureLeftChildOfNewRoot = newRoot.left
            val futureLeftChildOfRightChildOfNewRoot = newRoot.right
            val futureRightChildOfRightChildOfNewRoot = right

            // new right child
            val newRootRightChild = Node(
                key, value, comparator, futureLeftChildOfRightChildOfNewRoot, futureRightChildOfRightChildOfNewRoot
            )
            // return newRoot
            return Node(newRoot.key, newRoot.value, comparator, futureLeftChildOfNewRoot, newRootRightChild)
        }

        private fun rotateLeft(): Node<K, V> {
            // store variables for quick access
            val newRoot = right ?: throw IllegalCallerException("Node's right child cannot be null")
            val futureRightChildOfNewRoot = newRoot.right
            val futureLeftChildOfLeftChildOfNewRoot = left
            val futureRightChildOfLeftChilfOfNewRoot = newRoot.left

            // new left child
            val newRootLeftChild = Node(
                key, value, comparator, futureLeftChildOfLeftChildOfNewRoot, futureRightChildOfLeftChilfOfNewRoot
            )
            return Node(newRoot.key, newRoot.value, comparator, newRootLeftChild, futureRightChildOfNewRoot)
        }

        private fun balance(): Node<K, V> {
            val isRightSubtreeTooHigh = balanceFactor == BALANCE_FACTOR_RIGHT_SUBTREE_TOO_HIGH
            val isLeftSubtreeTooHigh = balanceFactor == BALANCE_FACTOR_LEFT_SUBTREE_TOO_HIGH

            return if (right != null && isRightSubtreeTooHigh) {
                // node.right is not null because balanceFactor == 2
                val newRight = if (right.balanceFactor < 0) right.rotateRight() else right
                Node(key, value, comparator, left, newRight).rotateLeft()
            } else if (left != null && isLeftSubtreeTooHigh) {
                // node.left is not null because balanceFactor == -2
                val newLeft = if (left.balanceFactor > 0) left.rotateLeft() else left
                Node(key, value, comparator, newLeft, right).rotateRight()
            } else {
                // already balanced
                this
            }
        }

        /**
         * Finds the [V] value that corresponds to the received [K] key
         * @param key hat should be found
         * @return the value that is found or null
         * if the following homeworks.hw3.ImmutableAVLTree does not contain the received key
         */
        fun get(key: K): V? {
            // key == this.key
            if (comparator.compare(key, this.key) == 0) {
                return this.value
            }
            val destination = if (comparator.compare(key, this.key) < 0) left else right
            return destination?.get(key)
        }

        /**
         * Sets the value of received key
         * @param key is the key of the (key, value) Map Entry
         * @param value that should be set
         * @return the root of the new subtree
         */
        fun set(key: K, value: V): Node<K, V> {
            // key == this.key
            if (comparator.compare(key, this.key) == 0) {
                // update this node
                return Node(this.key, value, comparator, left, right)
            }

            val shouldGoLeft = comparator.compare(key, this.key) < 0

            return if (shouldGoLeft) {
                // key < this.key
                val newLeft = left?.set(key, value) ?: Node(key, value, comparator)
                Node(this.key, this.value, comparator, newLeft, right).balance()
            } else {
                // key > this.key
                val newRight = right?.set(key, value) ?: Node(key, value, comparator)
                Node(this.key, this.value, comparator, left, newRight).balance()
            }
        }

        private fun findNodeWithMinKey(): Node<K, V> {
            if (left == null) {
                return this
            }
            return left.findNodeWithMinKey()
        }

        /**
         * Removes the entry with the following key
         * @param key of the entry that should be removed
         * @return new root of the subtree
         */
        fun remove(key: K): Node<K, V>? {
            if (comparator.compare(key, this.key) == 0) {
                // key == this.key
                // this is the node that should be deleted
                return if (right == null) {
                    left
                } else {
                    val newRoot = right.findNodeWithMinKey()
                    val newRight = right.remove(newRoot.key)

                    Node(newRoot.key, newRoot.value, comparator, left, newRight).balance()
                }
            }

            return if (comparator.compare(key, this.key) < 0) {
                // key < this.key
                val newLeft = left?.remove(key)
                Node(this.key, this.value, comparator, newLeft, right)
            } else {
                // key > this.key
                val newRight = right?.remove(key)
                Node(this.key, this.value, comparator, left, newRight)
            }
        }

        override fun iterator(): Iterator<Node<K, V>> {
            val stack = Stack<Node<K, V>>()
            stack.push(this)

            return sequence<Node<K, V>> {
                while (stack.isNotEmpty()) {
                    val curNode = stack.pop()
                    for (child in curNode.children) {
                        stack.push(child)
                    }
                    yield(curNode)
                }
            }.iterator()
        }

        override fun toString(): String {
            return "($key, $value)"
        }

        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }

    override fun containsKey(key: K): Boolean {
        return get(key) != null
    }

    override fun containsValue(value: V): Boolean {
        return this.entries.any { it.value == value }
    }

    override fun get(key: K): V? {
        return root?.get(key)
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     * Puts a value in the AVLTree. (Like MutableMap.put)
     * However, does not change the tree itself but returns the new updated tree
     * @param key of the entry to put
     * @param value of the entry to put
     * @return new updated tree
     */
    override fun put(key: K, value: V): V? {
        val treeRoot = root
        // tree is empty
        return if (treeRoot == null) {
            root = Node(key, value, comparator)
            null
        } else {
            val prevValue = get(key)
            root = treeRoot.set(key, value)
            prevValue
        }
    }

    /**
     * Removes the entry with the received [key]
     * However, similarly to `put` does not update the following tree, but returns a new updated tree
     * @param key of the entry that should be removed
     * @return new updated tree
     */
    override fun remove(key: K): V? {
        val treeRoot = root
        val value = get(key)
        return if (treeRoot == null || value == null) {
            null
        } else {
            root = treeRoot.remove(key)
            value
        }
    }

    override fun clear() {
        root = null
    }

    override fun putAll(from: Map<out K, V>) {
        for ((key, value) in from.entries) {
            put(key, value)
        }
    }
}
