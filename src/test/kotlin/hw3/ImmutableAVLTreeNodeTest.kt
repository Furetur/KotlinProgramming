package hw3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail


val stringComparator = Comparator<String> { str1, str2 -> str1.compareTo(str2) }

val leftChild = ImmutableAVLTree.Node("a", 1)
val rightChildOfRightChild = ImmutableAVLTree.Node("e", 5)
val leftChildOfRightChild = ImmutableAVLTree.Node("c", 4)
val rightChild = ImmutableAVLTree.Node("d", 3, leftChildOfRightChild, rightChildOfRightChild)

val root = ImmutableAVLTree.Node("b", 2, leftChild, rightChild)



internal class ImmutableAVLTreeNodeTest {

    @Test
    fun get_shouldReturnCorrectValues1() {
        assertEquals(1, root.get("a", stringComparator))
    }

    @Test
    fun get_shouldReturnCorrectValues2() {
        assertEquals(5, root.get("e", stringComparator))
    }

    @Test
    fun get_shouldReturnCorrectValues3() {
        assertEquals(4, root.get("c", stringComparator))
    }

    @Test
    fun get_shouldReturnCorrectValues4() {
        assertEquals(3, root.get("d", stringComparator))
    }

    @Test
    fun get_shouldReturnCorrectValues5() {
        assertEquals(2, root.get("b", stringComparator))
    }

    @Test
    fun set_shouldSetTheRootsValue() {
        val newRoot = root.set("b", 3, stringComparator)
        assertEquals(3, newRoot.value)
    }

    @Test
    fun set_shouldSetTheMostRemoteNodeValueThatShouldBeAccessedByGet() {
        val newRoot = root.set("e", 6, stringComparator)
        assertEquals(6, newRoot.get("e", stringComparator))
    }

    @Test
    fun set_shouldAddNewNodesThatCanBeAccessedByGet() {
        val newRoot = root.set("ab", 101, stringComparator)
        assertEquals(101, newRoot.get("ab", stringComparator))
    }

    @Test
    fun set_shouldWorkWithEmptyStrings() {
        val newRoot = root.set("", 909, stringComparator)
        assertEquals(909, newRoot.get("", stringComparator))
    }

    @Test
    fun remove_shouldRemoveRoot() {
        val newRoot = root.remove("b", stringComparator) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("b", stringComparator))
    }

    @Test
    fun remove_shouldRemoveRootOfSubtree() {
        val newRoot = root.remove("d", stringComparator) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("d", stringComparator))
    }

    @Test
    fun remove_shouldRemoveLeaf1() {
        val newRoot = root.remove("c", stringComparator) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("c", stringComparator))
    }

    @Test
    fun remove_shouldRemoveLeaf2() {
        val newRoot = root.remove("e", stringComparator) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("e", stringComparator))
    }

    @Test
    fun remove_shouldRemoveLeaf3() {
        val newRoot = root.remove("a", stringComparator) ?: fail("new root should not be null")

        assertEquals(null, newRoot.get("a", stringComparator))
    }
}