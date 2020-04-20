package homeworks.hw4.task2

import java.lang.IllegalArgumentException

val supportedOperators = listOf('+', '/', '-', '*')

class ExpressionTree(private val root: Node) {

    class IllegalStringSyntax(override val message: String) : IllegalArgumentException()

    class UnsupportedOperatorException : IllegalArgumentException()

    interface Node {
        fun evaluate(): Double
        override fun toString(): String
    }

    class OperatorNode(private val operator: Char, private val operand1: Node, private val operand2: Node) : Node {
        init {
            if (!supportedOperators.contains(operator)) {
                throw UnsupportedOperatorException()
            }
        }

        override fun evaluate(): Double {
            val leftResult = operand1.evaluate()
            val rightResult = operand2.evaluate()

            return when (operator) {
                '+' -> leftResult + rightResult
                '-' -> leftResult - rightResult
                '*' -> leftResult * rightResult
                '/' -> leftResult / rightResult
                else -> throw UnsupportedOperatorException()
            }
        }

        override fun toString(): String {
            return "($operator $operand1 $operand2)"
        }
    }

    class ValueNode(private val value: Int) : Node {
        override fun evaluate(): Double {
            return value.toDouble()
        }

        override fun toString(): String {
            return value.toString()
        }
    }

    fun evaluate(): Double {
        return root.evaluate()
    }

    override fun toString(): String {
        return root.toString()
    }
}
