package homeworks.hw4.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ExpressionTreeTest {
    @Test
    fun `ValueNode should eval to its value 1`() {
        val node = ExpressionTree.ValueNode(4)
        assertEquals(4.0, node.evaluate())
    }

    @Test
    fun `ValueNode should eval to its value 2`() {
        val node = ExpressionTree.ValueNode(2345)
        assertEquals(2345.0, node.evaluate())
    }

    @Test
    fun `OperatorNode should evaluate 1 + 2`() {
        val op1 = ExpressionTree.ValueNode(1)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('+', op1, op2)
        assertEquals(3.0, node.evaluate())
    }

    @Test
    fun `OperatorNode should evaluate 5 - 7`() {
        val op1 = ExpressionTree.ValueNode(5)
        val op2 = ExpressionTree.ValueNode(7)
        val node = ExpressionTree.OperatorNode('-', op1, op2)
        assertEquals(-2.0, node.evaluate())
    }

    @Test
    fun `OperatorNode should evaluate 5 * 2`() {
        val op1 = ExpressionTree.ValueNode(5)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('*', op1, op2)
        assertEquals(10.0, node.evaluate())
    }

    @Test
    fun `OperatorNode should evaluate 10 divided by 2`() {
        val op1 = ExpressionTree.ValueNode(10)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('/', op1, op2)
        assertEquals(5.0, node.evaluate())
    }

    @Test
    fun `OperatorNode should evaluate double values`() {
        val op1 = ExpressionTree.ValueNode(5)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('/', op1, op2)
        assertEquals(2.5, node.evaluate())
    }

    private fun buildSum(height: Int): ExpressionTree.Node {
        return if (height == 0) {
            ExpressionTree.ValueNode(1)
        } else {
            return ExpressionTree.OperatorNode(
                '+',
                buildSum(height - 1),
                buildSum(height - 1)
            )
        }
    }

    @Test
    fun `OperatorNode should evaluate big sums`() {
        val node = buildSum(10)
        assertEquals(1024.0, node.evaluate())
    }

    @Test
    fun `OperatorNode should divide other operator nodes`() {
        val op1 = buildSum(10)
        val op2 = buildSum(10)
        val node = ExpressionTree.OperatorNode('/', op1, op2)
        assertEquals(1.0, node.evaluate())
    }

    private fun buildTreeResultingInTwo(height: Int): ExpressionTree.OperatorNode {
        val op1 = buildSum(height + 1)
        val op2 = buildSum(height)
        return ExpressionTree.OperatorNode('/', op1, op2)
    }

    @Test
    fun `OperatorNode should eval big tree with one division`() {
        assertEquals(2.0, buildTreeResultingInTwo(10).evaluate())
    }

    @Test
    fun `OperatorNode should throw if given unsupported operator`() {
        assertThrows(ExpressionTree.UnsupportedOperatorException::class.java) {
            ExpressionTree.OperatorNode(
                '&', ExpressionTree.ValueNode(1), ExpressionTree.ValueNode(2)
            )
        }
    }

    private fun buildTreeResultingInHalf(height: Int): ExpressionTree.OperatorNode {
        val op1 = buildSum(height)
        val op2 = buildSum(height + 1)
        return ExpressionTree.OperatorNode('/', op1, op2)
    }

    private fun buildTreeResultingInOne(height: Int): ExpressionTree.OperatorNode {
        val op1 = buildTreeResultingInTwo(height)
        val op2 = buildTreeResultingInHalf(height)
        return ExpressionTree.OperatorNode('*', op1, op2)
    }

    private fun buildSuperSum(height: Int): ExpressionTree.OperatorNode {
        return if (height == 0) {
            buildTreeResultingInOne(5)
        } else {
            return ExpressionTree.OperatorNode(
                '+',
                buildSuperSum(height - 1),
                buildSuperSum(height - 1)
            )
        }
    }

    @Test
    fun `OperatorNode should eval big tree with addition division and multiplication`() {
        val node = buildSuperSum(10)
        assertEquals(1024.0, node.evaluate())
    }

    @Test
    fun `ExpressionTree should eval root`() {
        val tree = ExpressionTree(buildSuperSum(15))
        assertEquals(32768.0, tree.evaluate())
    }

    @Test
    fun `ValueNode should stringify into its value 1`() {
        val node = ExpressionTree.ValueNode(1010)
        assertEquals("1010", node.toString())
    }

    @Test
    fun `ValueNode should stringify into its value 2`() {
        val node = ExpressionTree.ValueNode(101)
        assertEquals("101", node.toString())
    }

    @Test
    fun `OperatorNode 1 + 2 should stringify`() {
        val op1 = ExpressionTree.ValueNode(1)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('+', op1, op2)
        assertEquals("(+ 1 2)", node.toString())
    }

    @Test
    fun `OperatorNode 5 - 7 should stringify`() {
        val op1 = ExpressionTree.ValueNode(5)
        val op2 = ExpressionTree.ValueNode(7)
        val node = ExpressionTree.OperatorNode('-', op1, op2)
        assertEquals("(- 5 7)", node.toString())
    }

    @Test
    fun `OperatorNode 5 * 2 should stringify`() {
        val op1 = ExpressionTree.ValueNode(5)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('*', op1, op2)
        assertEquals("(* 5 2)", node.toString())
    }

    @Test
    fun `OperatorNode 10 divide by 2 should stringify`() {
        val op1 = ExpressionTree.ValueNode(10)
        val op2 = ExpressionTree.ValueNode(2)
        val node = ExpressionTree.OperatorNode('/', op1, op2)
        assertEquals("(/ 10 2)", node.toString())
    }

    @Test
    fun `OperatorNode should stringify big sum of 4`() {
        val node = buildSum(2)
        val expected = "(+ (+ 1 1) (+ 1 1))"
        val actual = node.toString()
        assertEquals(expected, actual)
    }

    @Test
    fun `OperatorNode should stringify big sum of 16`() {
        val node = buildSum(4)
        val expected = "(+ (+ (+ (+ 1 1) (+ 1 1)) (+ (+ 1 1) (+ 1 1))) (+ (+ (+ 1 1) (+ 1 1)) (+ (+ 1 1) (+ 1 1))))"
        val actual = node.toString()
        assertEquals(expected, actual)
    }
}
