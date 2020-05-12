package homeworks.hw3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import kotlin.Comparator
import kotlin.math.abs
import kotlin.math.max

internal class ImmutableAVLTreeNodeTest {

    private val stringComparator = Comparator<String> { str1, str2 -> str1.compareTo(str2) }

    private var root: ImmutableAVLTree.Node<String, Int> = ImmutableAVLTree.Node("k", 1, stringComparator)

    init {
        val leftChild = ImmutableAVLTree.Node("a", 1, stringComparator)
        val rightChildOfRightChild = ImmutableAVLTree.Node("e", 5, stringComparator)
        val leftChildOfRightChild = ImmutableAVLTree.Node("c", 4, stringComparator)
        val rightChild = ImmutableAVLTree.Node(
            "d", 3, stringComparator, leftChildOfRightChild, rightChildOfRightChild
        )
        root = ImmutableAVLTree.Node("b", 2, stringComparator, leftChild, rightChild)
    }

    // returns -1 if the subtree is not balanced
    private fun <K, V> checkBalance(node: ImmutableAVLTree.Node<K, V>): Int {
        val left = node.left
        val right = node.right
        val leftHeight = if (left != null) checkBalance(left) else 0
        val rightHeight = if (right != null) checkBalance(right) else 0

        return if (leftHeight == -1 || rightHeight == -1) {
            -1
        } else if (abs(leftHeight - rightHeight) > 1) {
            -1
        } else {
            max(leftHeight, rightHeight) + 1
        }
    }

    private fun buildBigTree(size: Int): ImmutableAVLTree.Node<String, Int> {
        var node = ImmutableAVLTree.Node<String, Int>("0", 0, stringComparator)
        for (i in 1..size) {
            node = node.set("key-$i", i)
        }
        return node
    }

    @Test
    fun `get should return correct value of left child`() {
        assertEquals(1, root.get("a"))
    }

    @Test
    fun `get should return correct value of the rightest child`() {
        assertEquals(5, root.get("e"))
    }

    @Test
    fun `get should return correct value of left child of right child`() {
        assertEquals(4, root.get("c"))
    }

    @Test
    fun `get should return correct value of the right child`() {
        assertEquals(3, root.get("d"))
    }

    @Test
    fun `get should return value of root`() {
        assertEquals(2, root.get("b"))
    }

    @Test
    fun `set should update the roots value`() {
        val newRoot = root.set("b", 3)
        assertEquals(3, newRoot.value)
    }

    @Test
    fun `set should update the most remote node`() {
        val newRoot = root.set("e", 6)
        assertEquals(6, newRoot.get("e"))
    }

    @Test
    fun `set should add new nodes that can be accessed by get`() {
        val newRoot = root.set("ab", 101)
        assertEquals(101, newRoot.get("ab"))
    }

    @Test
    fun `set should work with empty string keys`() {
        val newRoot = root.set("", 909)
        assertEquals(909, newRoot.get(""))
    }

    @Test
    fun `remove should be able to remove the root`() {
        val newRoot = root.remove("b")
        assertNotNull(newRoot)
        if (newRoot != null) {
            assertEquals(null, newRoot.get("b"))
        }
    }

    @Test
    fun `remove should be able to remove the root of subtree`() {
        val newRoot = root.remove("d")
        assertNotNull(newRoot)
        if (newRoot != null) {
            assertEquals(null, newRoot.get("d"))
        }
    }

    @Test
    fun `remove should remove leaf 1`() {
        val newRoot = root.remove("c")
        assertNotNull(newRoot)
        if (newRoot != null) {
            assertEquals(null, newRoot.get("c"))
        }
    }

    @Test
    fun `remove should remove leaf 2`() {
        val newRoot = root.remove("e")
        assertNotNull(newRoot)
        if (newRoot != null) {
            assertEquals(null, newRoot.get("e"))
        }
    }

    @Test
    fun `remove should remove leaf 3`() {
        val newRoot = root.remove("a")
        assertNotNull(newRoot)
        if (newRoot != null) {
            assertEquals(null, newRoot.get("a"))
        }
    }

    // balance tests

    @Test
    fun `subtree of 100 elements should remain balanced`() {
        val node = buildBigTree(100)
        assertNotEquals(-1, checkBalance(node))
    }

    @Test
    fun `subtree of 1000 elements should remain balanced`() {
        val node = buildBigTree(1000)
        assertNotEquals(-1, checkBalance(node))
    }
}
