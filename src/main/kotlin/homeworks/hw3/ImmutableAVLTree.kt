package homeworks.hw3

import java.lang.Integer.max
import java.util.Stack
import kotlin.Comparator

const val BALANCE_FACTOR_RIGHT_SUBTREE_TOO_HIGH = 2
const val BALANCE_FACTOR_LEFT_SUBTREE_TOO_HIGH = -2

/**
 * AVLTree implementation of Map
 * @param K type of keys
 * @param V type of values
 * @param comparator is used to compare keys
 * @param root is used internally by AVLTree, represents the tree
 * @param rootSize number of nodes in the root's subtree
 * @constructor Creates an empty AVLTree if parameters root and rootSize are not received,
 * acts as a wrapper around [root] otherwise
 */
class ImmutableAVLTree<K, V>(
    private val comparator: Comparator<K>,
    private val root: Node<K, V>? = null,
    private val rootSize: Int = 0
) : Map<K, V> {

    override val entries: Set<Map.Entry<K, V>>
        get() = root?.flatten()?.toSet() ?: setOf()

    override val keys: Set<K>
        get() = entries.map { entry -> entry.key }.toSet()

    override val size: Int
        get() = rootSize

    override val values: Collection<V>
        get() = entries.map { entry -> entry.value }

    /**
     * A basic implementation of Map.Entry
     */
    class Entry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V> {
        override fun toString(): String {
            return "($key, $value)"
        }
    }

    /**
     * Node of the ImmutableAVLTree
     * @param K type of the node's key
     * @param V type of the node's value
     * @constructor Creates a node and sets its left and right children
     */
    class Node<K, V>(val key: K, val value: V, val left: Node<K, V>? = null, val right: Node<K, V>? = null) {
        private val leftHeight: Int
            get() = left?.height ?: 0
        private val rightHeight: Int
            get() = right?.height ?: 0

        private val height: Int = max(leftHeight, rightHeight) + 1
        private val balanceFactor: Int = rightHeight - leftHeight

        private fun rotateRight(): Node<K, V> {
            // store variables for quick access
            val willBecomeNewRoot = left ?: throw IllegalCallerException("Node's left child cannot be null")
            val willBecomeLeftChildOfNewRoot = willBecomeNewRoot.left
            val willBecomeLeftChildOfRightChildOfNewRoot = willBecomeNewRoot.right
            val willBecomeRightChildOfRightChildOfNewRoot = right

            // new right child
            val newRootRightChild =
                Node(
                    key,
                    value,
                    willBecomeLeftChildOfRightChildOfNewRoot,
                    willBecomeRightChildOfRightChildOfNewRoot
                )
            // return newRoot
            return Node(
                willBecomeNewRoot.key,
                willBecomeNewRoot.value,
                willBecomeLeftChildOfNewRoot,
                newRootRightChild
            )
        }

        private fun rotateLeft(): Node<K, V> {
            // store variables for quick access
            val willBecomeNewRoot = right ?: throw IllegalCallerException("Node's right child cannot be null")
            val willBecomeRightChildOfNewRoot = willBecomeNewRoot.right
            val willBecomeLeftChildOfLeftChildOfNewRoot = left
            val willBecomeRightChildOfLeftChildOfNewRoot = willBecomeNewRoot.left

            // new left child
            val newRootLeftChild =
                Node(
                    key,
                    value,
                    willBecomeLeftChildOfLeftChildOfNewRoot,
                    willBecomeRightChildOfLeftChildOfNewRoot
                )
            return Node(
                willBecomeNewRoot.key,
                willBecomeNewRoot.value,
                newRootLeftChild,
                willBecomeRightChildOfNewRoot
            )
        }

        private fun balance(): Node<K, V> {
            val rightSubtreeIsTooHigh = balanceFactor == BALANCE_FACTOR_RIGHT_SUBTREE_TOO_HIGH
            val leftSubtreeIsTooHigh = balanceFactor == BALANCE_FACTOR_LEFT_SUBTREE_TOO_HIGH

            if (!rightSubtreeIsTooHigh && !leftSubtreeIsTooHigh) {
                // already balanced
                return this
            }

            return if (rightSubtreeIsTooHigh) {
                // node.right is not null because balanceFactor == 2
                val newRight = if (right!!.balanceFactor < 0) right.rotateRight() else right
                Node(key, value, left, newRight).rotateLeft()
            } else {
                // node.left is not null because balanceFactor == -2
                val newLeft = if (left!!.balanceFactor > 0) left.rotateLeft() else left
                Node(key, value, newLeft, right).rotateRight()
            }
        }

        /**
         * Finds the [V] value that corresponds to the received [K] key
         * @param key hat should be found
         * @param comparator is used to compare the keys
         * @return the value that is found or null
         * if the following homeworks.hw3.ImmutableAVLTree does not contain the received key
         */
        fun get(key: K, comparator: Comparator<K>): V? {
            // key == this.key
            if (comparator.compare(key, this.key) == 0) {
                return this.value
            }
            val destination = if (comparator.compare(key, this.key) < 0) left else right
            return destination?.get(key, comparator)
        }

        /**
         * Sets the value of received key
         * @param key is the key of the (key, value) Map Entry
         * @param value that should be set
         * @return the root of the new subtree
         */
        fun set(key: K, value: V, comparator: Comparator<K>): Node<K, V> {
            // key == this.key
            if (comparator.compare(key, this.key) == 0) {
                // update this node
                return Node(this.key, value, left, right)
            }

            val shouldGoLeft = comparator.compare(key, this.key) < 0

            return if (shouldGoLeft) {
                // key < this.key
                val newLeft = left?.set(key, value, comparator) ?: Node(
                    key,
                    value
                )
                Node(this.key, this.value, newLeft, right).balance()
            } else {
                // key > this.key
                val newRight = right?.set(key, value, comparator) ?: Node(
                    key,
                    value
                )
                Node(this.key, this.value, left, newRight).balance()
            }
        }

        private fun findNodeWithMinKey(): Node<K, V> {
            if (left == null) {
                return this
            }
            return left.findNodeWithMinKey()
        }

        private fun removeNodeWithMinKey(): Node<K, V>? {
            if (left == null) {
                // removes the current node
                return right
            }
            val newLeft = left.removeNodeWithMinKey()
            return Node(key, value, newLeft, right).balance()
        }

        /**
         * Removes the entry with the following key
         * @param key of the entry that should be removed
         * @param comparator is used to compare the keys
         * @return new root of the subtree
         */
        fun remove(key: K, comparator: Comparator<K>): Node<K, V>? {
            if (comparator.compare(key, this.key) == 0) {
                // key == this.key
                // this is the node that should be deleted
                return if (right == null) {
                    left
                } else {
                    val newRoot = right.findNodeWithMinKey()
                    val newRight = right.removeNodeWithMinKey()

                    Node(newRoot.key, newRoot.value, left, newRight).balance()
                }
            }

            return if (comparator.compare(key, this.key) < 0) {
                // key < this.key
                val newLeft = left?.remove(key, comparator)
                Node(this.key, this.value, newLeft, right)
            } else {
                // key > this.key
                val newRight = right?.remove(key, comparator)
                Node(this.key, this.value, left, newRight)
            }
        }

        /**
         * Converts the following subtree to a list
         */
        fun flatten(): MutableList<Map.Entry<K, V>> {
            val resultList = mutableListOf<Map.Entry<K, V>>()
            val leftPart = left?.flatten() ?: mutableListOf()
            val rightPart = right?.flatten() ?: mutableListOf()
            val currentMapEntry: Map.Entry<K, V> = Entry(key, value)

            resultList.addAll(leftPart)
            resultList.add(currentMapEntry)
            resultList.addAll(rightPart)

            return resultList
        }
    }

    override fun containsKey(key: K): Boolean {
        return get(key) != null
    }

    @Suppress("ReturnCount")
    private fun breadthFirstSearch(valuePredicate: (value: V) -> Boolean): Boolean {
        if (root == null) {
            return false
        }
        val stack = Stack<Node<K, V>>()
        stack.push(root)

        while (!stack.empty()) {
            val curNode = stack.pop()
            if (valuePredicate(curNode.value)) {
                return true
            }
            if (curNode.left != null) {
                stack.push(curNode.left)
            }
            if (curNode.right != null) {
                stack.push(curNode.right)
            }
        }
        return false
    }

    override fun containsValue(value: V): Boolean {
        val equalityPredicate = { treeValue: V -> treeValue == value }
        return breadthFirstSearch(equalityPredicate)
    }

    override fun get(key: K): V? {
        return root?.get(key, comparator)
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
    fun put(key: K, value: V): ImmutableAVLTree<K, V> {
        // tree is empty
        if (root == null) {
            val newRoot = Node(key, value)
            return ImmutableAVLTree(comparator, newRoot, 1)
        }
        // tree is not empty
        val newRoot = root.set(key, value, comparator)
        val newSize = if (!containsKey(key)) size + 1 else size
        return ImmutableAVLTree(comparator, newRoot, newSize)
    }

    /**
     * Removes the entry with the received [key]
     * However, similarly to `put` does not update the folowing tree, but returns a new updated tree
     * @param key of the entry that should be removed
     * @return new updated tree
     */
    fun remove(key: K): ImmutableAVLTree<K, V> {
        if (!containsKey(key)) {
            return this
        }
        // contains the key
        // therefore root is not null
        val newRoot = root!!.remove(key, comparator)
        val newSize = size - 1
        return ImmutableAVLTree(comparator, newRoot, newSize)
    }
}
