package homeworks.hw4.task2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

internal class ParseExpressionTreeKtTest {

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

    private fun buildTreeResultingInTwo(height: Int): ExpressionTree.OperatorNode {
        val op1 = buildSum(height + 1)
        val op2 = buildSum(height)
        return ExpressionTree.OperatorNode('/', op1, op2)
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
    fun `should parse 1 + 2`() {
        val input = "(+ 1 2)"
        val tree = parseExpressionTree(input)
        assertEquals(3.0, tree.evaluate())
    }

    @Test
    fun `should parse 5 - 2`() {
        val input = "(- 5 2)"
        val tree = parseExpressionTree(input)
        assertEquals(3.0, tree.evaluate())
    }

    @Test
    fun `should parse 5 divided by 2`() {
        val input = "(/ 5 2)"
        val tree = parseExpressionTree(input)
        assertEquals(2.5, tree.evaluate())
    }

    @Test
    fun `should parse 10 multiplied by 5`() {
        val input = "(* 10 5)"
        val tree = parseExpressionTree(input)
        assertEquals(50.0, tree.evaluate())
    }

    @Test
    fun `should parse generated big sum`() {
        val input = buildSum(10).toString()
        val tree = parseExpressionTree(input)
        assertEquals(1024.0, tree.evaluate())
    }

    @Test
    fun `should parse generated super big sum`() {
        val input = buildSuperSum(10).toString()
        val tree = parseExpressionTree(input)
        assertEquals(1024.0, tree.evaluate())
    }

    @Test
    fun `should parse a number`() {
        val input = "178"
        val tree = parseExpressionTree(input)
        assertEquals(178.0, tree.evaluate())
    }

    @Test
    fun `should throw if value node is written with not only numbers`() {
        try {
            parseExpressionTree("112.0")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has a missing parenthesis`() {
        try {
            parseExpressionTree("(+ 1 2")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node missing parenthesis`() {
        try {
            parseExpressionTree("+ 1 2")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has less than 2 operands`() {
        try {
            parseExpressionTree("(+ 1)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has more than 2 operands`() {
        try {
            parseExpressionTree("(+ 1 2 3 4)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has any symbols around it 1`() {
        try {
            parseExpressionTree(" (+ 1 2 3 4)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has any symbols around it 2`() {
        try {
            parseExpressionTree("(+ 1 2 3 4)  ")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has unsupported operation 1`() {
        try {
            parseExpressionTree("(. 1 2)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if operator node has unsupported operation 2`() {
        try {
            parseExpressionTree("(| 1 2)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if first operand is illegal`() {
        try {
            parseExpressionTree("(+ (+ 2)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if first operand is empty`() {
        try {
            parseExpressionTree("(+ () 2)")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if second operand is empty`() {
        try {
            parseExpressionTree("(+ 1 ())")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

    @Test
    fun `should throw if second operand is illegal`() {
        try {
            parseExpressionTree("(+ 1 (+ 10))")
            fail("Should throw IllegalStringSyntax")
        } catch (e: ExpressionTree.IllegalStringSyntax) {
            // success
        }
    }

}