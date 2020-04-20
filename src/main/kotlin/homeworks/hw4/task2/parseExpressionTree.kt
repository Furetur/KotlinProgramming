package homeworks.hw4.task2

import java.lang.IllegalArgumentException

val operatorNodeRegExp = Regex("^[(]([+]|[-]|[*]|[/]) .+ .+[)]$")
val valueNodeRegex = Regex("^[0-9]+$")

const val FIRST_OPERAND_START_INDEX = 3

private fun findEndOfParenthesisSequence(str: String): Int {
    if (str[0] != '(') {
        throw IllegalArgumentException("First character should be (")
    }
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

private fun splitTwoOperands(twoOperands: String): List<String> {
    val firstOperandEnd = if (twoOperands[0] == '(') {
        findEndOfParenthesisSequence(twoOperands)
    } else {
        twoOperands.indexOfFirst { it == ' ' } - 1
    }
    if (firstOperandEnd == twoOperands.length - 1 || firstOperandEnd == -1) {
        throw ExpressionTree.IllegalStringSyntax()
    }

    val firstOperand = twoOperands.substring(0, firstOperandEnd + 1)
    val secondOperand = twoOperands.substring(firstOperandEnd + 2)
    return listOf(firstOperand, secondOperand)
}

private fun parseOperatorNode(str: String): ExpressionTree.OperatorNode {
    val operator = str[1]
    val twoOperands = str.substring(FIRST_OPERAND_START_INDEX, str.length - 1)
    val (op1, op2) = splitTwoOperands(twoOperands)
    val operandNode1 = parseNode(op1)
    val operandNode2 = parseNode(op2)
    return ExpressionTree.OperatorNode(operator, operandNode1, operandNode2)
}

private fun parseValueNode(str: String): ExpressionTree.ValueNode {
    val numericalValue = str.toIntOrNull() ?: throw ExpressionTree.IllegalStringSyntax()
    return ExpressionTree.ValueNode(numericalValue)
}

private fun parseNode(str: String): ExpressionTree.Node {
    if (str.isEmpty()) {
        throw ExpressionTree.IllegalStringSyntax()
    }
    return when {
        operatorNodeRegExp.matches(str) -> {
            parseOperatorNode(str)
        }
        valueNodeRegex.matches(str) -> {
            parseValueNode(str)
        }
        else -> {
            throw ExpressionTree.IllegalStringSyntax()
        }
    }
}

fun parseExpressionTree(str: String): ExpressionTree {
    return ExpressionTree(parseNode(str))
}
