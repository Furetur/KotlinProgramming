package homeworks.hw4.task2

import java.lang.IllegalArgumentException

class ExpressionTree(private val root: Node) {

    companion object {
        val supportedOperators = listOf('+', '/', '-', '*')
    }

    class UnsupportedOperatorException(message: String) : IllegalArgumentException(message)

    interface Node {
        fun evaluate(): Double
        override fun toString(): String
    }

    class OperatorNode(val operator: Char, val operand1: Node, val operand2: Node) : Node {

        override fun evaluate(): Double {
            val leftResult = operand1.evaluate()
            val rightResult = operand2.evaluate()

            return when (operator) {
                '+' -> leftResult + rightResult
                '-' -> leftResult - rightResult
                '*' -> leftResult * rightResult
                '/' -> leftResult / rightResult
                else -> throw UnsupportedOperatorException("Operator $operator is not supported")
            }
        }

        override fun toString(): String {
            return "($operator $operand1 $operand2)"
        }

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is OperatorNode -> operator == other.operator &&
                        operand1 == other.operand1 && operand2 == other.operand2
                else -> false
            }
        }

        override fun hashCode(): Int {
            var result = operator.hashCode()
            result = 31 * result + operand1.hashCode()
            result = 31 * result + operand2.hashCode()
            return result
        }
    }

    class ValueNode(val value: Int) : Node {
        override fun evaluate(): Double {
            return value.toDouble()
        }

        override fun toString(): String {
            return value.toString()
        }

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is ValueNode -> value == other.value
                else -> false
            }
        }

        override fun hashCode(): Int {
            return value
        }
    }

    fun evaluate(): Double {
        return root.evaluate()
    }

    override fun toString(): String {
        return root.toString()
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ExpressionTree -> root == other.root
            else -> false
        }
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }
}
