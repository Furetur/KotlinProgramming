package homeworks.hw4.task2

import java.lang.IllegalArgumentException

class ExpressionTreeBuilder(private val input: String) {
    companion object {
        val operatorNodeRegExp = Regex("^[(]. .+ .+[)]$")
        val numberRegex = Regex("^[-]?[0-9]*[.|,]?[0-9]+\$")
        const val FIRST_OPERAND_START_INDEX = 3
    }

    class IllegalStringSyntax(message: String) : IllegalArgumentException(message)

    fun parseExpressionTree(): ExpressionTree {
        return ExpressionTree(parseNode(input))
    }

    private fun parseNode(str: String): ExpressionTree.Node {
        if (str.isEmpty()) {
            throw IllegalStringSyntax("Received empty node")
        }
        return when {
            operatorNodeRegExp.matches(str) -> {
                parseOperatorNode(str)
            }
            numberRegex.matches(str) -> {
                parseValueNode(str)
            }
            else -> {
                throw IllegalStringSyntax(
                    "Node or integer expected but received \"$str\"\n" +
                            "\tCheck for unclosed parenthesis and odd whitespaces\n" +
                            "\tNodes should look like " +
                            "\"(<one character operator> <integer or node> <integer or node>)\"\n" +
                            "\tExample of node: (+ 1 2). Check for unclosed parenthesis and odd whitespaces"
                )
            }
        }
    }

    private fun parseOperatorNode(str: String): ExpressionTree.OperatorNode {
        val operator = str[1]
        val twoOperands = str.substring(FIRST_OPERAND_START_INDEX, str.length - 1)
        val operands = splitTwoOperands(twoOperands)
        val operandNode1 = parseNode(operands.leftExpression)
        val operandNode2 = parseNode(operands.rightExpression)
        return ExpressionTree.OperatorNode(operator, operandNode1, operandNode2)
    }

    private fun parseValueNode(str: String): ExpressionTree.ValueNode {
        val numericalValue = str.toIntOrNull() ?: throw IllegalStringSyntax(
            "Integer (whole number) expected but received \"$str\""
        )
        return ExpressionTree.ValueNode(numericalValue)
    }

    data class SplitBinaryOperationExpression(val leftExpression: String, val rightExpression: String)

    private fun splitTwoOperands(twoOperands: String): SplitBinaryOperationExpression {
        val firstOperandEnd = if (twoOperands[0] == '(') {
            findEndOfParenthesisSequence(twoOperands)
        } else {
            twoOperands.indexOfFirst { it == ' ' } - 1
        }
        if (firstOperandEnd == twoOperands.length - 1 || firstOperandEnd == -1) {
            throw IllegalStringSyntax(
                "\"<integer or node> <integer or node>\" expected but \"$twoOperands\" received. " +
                        "Check for unclosed parenthesis and odd whitespaces"
            )
        }

        val firstOperand = twoOperands.substring(0, firstOperandEnd + 1)
        val secondOperand = twoOperands.substring(firstOperandEnd + 2)
        return SplitBinaryOperationExpression(firstOperand, secondOperand)
    }

    private fun findEndOfParenthesisSequence(str: String): Int {
        assert(str[0] == '(')
        var parenthesisCounter = 0
        for (i in str.indices) {
            val curChar = str[i]
            if (curChar == '(') {
                parenthesisCounter += 1
            } else if (curChar == ')') {
                parenthesisCounter -= 1
            }
            if (parenthesisCounter == 0) {
                return i
            }
        }
        return -1
    }
}
