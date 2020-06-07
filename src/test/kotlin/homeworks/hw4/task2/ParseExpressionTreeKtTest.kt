package homeworks.hw4.task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

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
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        assertEquals(3.0, tree.evaluate())
    }

    @Test
    fun `should parse 5 - 2`() {
        val input = "(- 5 2)"
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        assertEquals(3.0, tree.evaluate())
    }

    @Test
    fun `should parse 5 divided by 2`() {
        val input = "(/ 5 2)"
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        assertEquals(2.5, tree.evaluate())
    }

    @Test
    fun `should parse 10 multiplied by 5`() {
        val input = "(* 10 5)"
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        assertEquals(50.0, tree.evaluate())
    }

    @Test
    fun `should parse generated big sum`() {
        val sum = buildSum(10)
        val input = sum.toString()
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        val expected = ExpressionTree(sum)
        assertEquals(expected, tree)
    }

    @Test
    fun `should parse generated super big sum`() {
        val sum = buildSuperSum(10)
        val input = sum.toString()
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        val expected = ExpressionTree(sum)
        assertEquals(expected, tree)
    }

    @Test
    fun `should parse a number`() {
        val input = "178"
        val tree = ExpressionTreeBuilder(input).parseExpressionTree()
        val expected = ExpressionTree(ExpressionTree.ValueNode(178))
        assertEquals(expected, tree)
    }

    @Test
    fun `should throw if value node is written with not only numbers`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("112.0").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node has a missing parenthesis`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ 1 2").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node missing parenthesis`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("+ 1 2").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node has less than 2 operands`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ 1)").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node has more than 2 operands`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ 1 2 3 4)").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node has any symbols around it 1`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder(" (+ 1 2 3 4)").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node has any symbols around it 2`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ 1 2 3 4)  ").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if operator node has unsupported operation 1`() {
        assertThrows(ExpressionTree.UnsupportedOperatorException::class.java) {
            ExpressionTreeBuilder("(. 1 2)").parseExpressionTree().evaluate()
        }
    }

    @Test
    fun `should throw if operator node has unsupported operation 2`() {
        assertThrows(ExpressionTree.UnsupportedOperatorException::class.java) {
            ExpressionTreeBuilder("(| 1 2)").parseExpressionTree().evaluate()
        }
    }

    @Test
    fun `should throw if first operand is illegal`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ (+ 2)").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if first operand is empty`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ () 2)").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if node is empty`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("()").parseExpressionTree()
        }
    }

    @Test
    fun `should if neither node nor integer is provided`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("wqe").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if second operand is empty`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ 1 ())").parseExpressionTree()
        }
    }

    @Test
    fun `should throw if second operand is illegal`() {
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder("(+ 1 (+ 10))").parseExpressionTree()
        }
    }

    private fun replaceDeeplyNested(bigString: String, oldStr: String, newStr: String): String {
        val newMiddle = bigString.substring(200, 300).replace(oldStr, newStr)
        return bigString.substring(0, 200) + newMiddle + bigString.substring(300)
    }

    @Test
    fun `should throw if deeply nested numbers is not an integer`() {
        val expected = buildSum(10)
        val input = expected.toString()
        val replacedInput = replaceDeeplyNested(input, "1", "1.0")
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder(replacedInput).parseExpressionTree()
        }
    }

    @Test
    fun `should throw if deeply nested nodes do not have parenthesis`() {
        val expected = buildSum(10)
        val input = expected.toString()
        val replacedInput = replaceDeeplyNested(input, "(", "")
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder(replacedInput).parseExpressionTree()
        }
    }

    @Test
    fun `should throw if deeply nested nodes have missing whitespaces`() {
        val expected = buildSum(10)
        val input = expected.toString()
        val replacedInput = replaceDeeplyNested(input, " ", "")
        assertThrows(ExpressionTreeBuilder.IllegalStringSyntax::class.java) {
            ExpressionTreeBuilder(replacedInput).parseExpressionTree()
        }
    }
}
