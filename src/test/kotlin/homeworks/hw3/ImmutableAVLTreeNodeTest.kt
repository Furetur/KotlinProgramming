package homeworks.hw3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.Comparator
import kotlin.math.abs
import kotlin.math.max

val stringComparator = Comparator<String> { str1, str2 -> str1.compareTo(str2) }

val leftChild = ImmutableAVLTree.Node("a", 1)
val rightChildOfRightChild = ImmutableAVLTree.Node("e", 5)
val leftChildOfRightChild = ImmutableAVLTree.Node("c", 4)
val rightChild = ImmutableAVLTree.Node(
    "d", 3,
    leftChildOfRightChild,
    rightChildOfRightChild
)

val root = ImmutableAVLTree.Node(
    "b", 2,
    leftChild,
    rightChild
)

internal class ImmutableAVLTreeNodeTest {

    @Test
    fun get_shouldReturnCorrectValues1() {
        assertEquals(
            1, root.get(
                "a",
                stringComparator
            )
        )
    }

    @Test
    fun get_shouldReturnCorrectValues2() {
        assertEquals(
            5, root.get(
                "e",
                stringComparator
            )
        )
    }

    @Test
    fun get_shouldReturnCorrectValues3() {
        assertEquals(
            4, root.get(
                "c",
                stringComparator
            )
        )
    }

    @Test
    fun get_shouldReturnCorrectValues4() {
        assertEquals(
            3, root.get(
                "d",
                stringComparator
            )
        )
    }

    @Test
    fun get_shouldReturnCorrectValues5() {
        assertEquals(
            2, root.get(
                "b",
                stringComparator
            )
        )
    }

    @Test
    fun set_shouldSetTheRootsValue() {
        val newRoot = root.set(
            "b", 3,
            stringComparator
        )
        assertEquals(3, newRoot.value)
    }

    @Test
    fun set_shouldSetTheMostRemoteNodeValueThatShouldBeAccessedByGet() {
        val newRoot = root.set(
            "e", 6,
            stringComparator
        )
        assertEquals(6, newRoot.get("e", stringComparator))
    }

    @Test
    fun set_shouldAddNewNodesThatCanBeAccessedByGet() {
        val newRoot = root.set(
            "ab", 101,
            stringComparator
        )
        assertEquals(101, newRoot.get("ab", stringComparator))
    }

    @Test
    fun set_shouldWorkWithEmptyStrings() {
        val newRoot = root.set(
            "", 909,
            stringComparator
        )
        assertEquals(909, newRoot.get("", stringComparator))
    }

    @Test
    fun remove_shouldRemoveRoot() {
        val newRoot = root.remove(
            "b",
            stringComparator
        ) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("b", stringComparator))
    }

    @Test
    fun remove_shouldRemoveRootOfSubtree() {
        val newRoot = root.remove(
            "d",
            stringComparator
        ) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("d", stringComparator))
    }

    @Test
    fun remove_shouldRemoveLeaf1() {
        val newRoot = root.remove(
            "c",
            stringComparator
        ) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("c", stringComparator))
    }

    @Test
    fun remove_shouldRemoveLeaf2() {
        val newRoot = root.remove(
            "e",
            stringComparator
        ) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("e", stringComparator))
    }

    @Test
    fun remove_shouldRemoveLeaf3() {
        val newRoot = root.remove(
            "a",
            stringComparator
        ) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("a", stringComparator))
    }

    // returns -1 if the subtree is not balanced
    private fun <K, V> checkBalance(node: ImmutableAVLTree.Node<K, V>): Int {
        val leftHeight = if (node.left != null) checkBalance(node.left!!) else 0
        val rightHeight = if (node.right != null) checkBalance(node.right!!) else 0

        return if (leftHeight == -1 || rightHeight == -1) {
            -1
        } else if (abs(leftHeight - rightHeight) > 1) {
            -1
        } else {
            max(leftHeight, rightHeight) + 1
        }
    }

    @Test
    fun `subtree of 10 elements should remain balanced`() {
        var node = ImmutableAVLTree.Node<String, Int>("0", 0, null, null)
        for (i in 1..10) {
            node = node.set(i.toString(), i, stringComparator)
        }
        assertNotEquals(-1, checkBalance(node))
    }

    @Test
    fun `subtree of 1000 elements should remain balanced`() {
        var node = ImmutableAVLTree.Node<String, Int>("0", 0, null, null)
        for (i in 1..1000) {
            node = node.set(i.toString(), i, stringComparator)
        }
        assertNotEquals(-1, checkBalance(node))
    }
}
