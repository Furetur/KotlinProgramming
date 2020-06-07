package homeworks.hw5

import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.util.Stack

class Trie : Serializable, Iterable<String> {
    private val root = Node()
    private var wordsNumberInRoot: Int = 0

    companion object {
        const val hashCodePrime = 31
    }

    val size: Int
        get() = wordsNumberInRoot

    private class Node(private var isTerminal: Boolean = false) : Iterable<String> {
        private val children = hashMapOf<Char, Node>()
        private var numTerminalChildren: Int = 0

        private fun getChild(symbol: Char, createNewIfDoesNotExist: Boolean = false): Node? {
            if (children[symbol] == null && createNewIfDoesNotExist) {
                children[symbol] = Node()
            }
            return children[symbol]
        }

        data class Path(val nodes: List<Node>, val destinationReached: Boolean)

        private fun traverse(value: String, createNewIfDoesNotExist: Boolean = false): Path {
            val path = mutableListOf<Node>()
            var curNode = this
            path.add(curNode)

            for (char in value) {
                val nextNode = curNode.getChild(char, createNewIfDoesNotExist) ?: return Path(path, false)
                curNode = nextNode
                path.add(curNode)
            }
            return Path(path, true)
        }

        fun add(value: String): Boolean {
            val pathNodes = traverse(value, true).nodes
            val isThisValueNew = !pathNodes.last().isTerminal
            if (isThisValueNew) {
                pathNodes.last().isTerminal = true
                for (node in pathNodes.asReversed().drop(1)) {
                    node.numTerminalChildren += 1
                }
            }
            return isThisValueNew
        }

        fun contains(value: String): Boolean {
            val path = traverse(value)
            return path.destinationReached && path.nodes.last().isTerminal
        }

        fun remove(value: String): Boolean {
            val path = traverse(value)
            val isValueInTrie = path.destinationReached && path.nodes.last().isTerminal
            if (isValueInTrie) {
                path.nodes.last().isTerminal = false
                for (node in path.nodes.asReversed().drop(1)) {
                    node.numTerminalChildren -= 1
                    if (node.numTerminalChildren == 0) {
                        node.children.clear()
                    }
                }
            }
            return isValueInTrie
        }

        fun howManyStartWithPrefix(value: String): Int {
            val path = traverse(value)
            val lastNode = path.nodes.last()
            return if (!path.destinationReached) {
                0
            } else if (lastNode.isTerminal) {
                lastNode.numTerminalChildren + 1
            } else {
                lastNode.numTerminalChildren
            }
        }

        override fun iterator(): Iterator<String> {
            val curPath = mutableListOf<String>()
            val stack = Stack<Pair<Node, String>?>()
            stack.push(Pair(this, ""))

            return sequence<String> {
                while (stack.isNotEmpty()) {
                    val curPair = stack.pop()
                    // null indicates that we have looked at all children of one node
                    // this means that we have to go up the tree
                    if (curPair == null) {
                        curPath.removeAt(curPath.lastIndex)
                        continue
                    }
                    // push null indicator before all children
                    stack.push(null)
                    val (curNode, curChar) = curPair
                    curPath.add(curChar)
                    // if this node holds a value that we yield it
                    if (curNode.isTerminal) {
                        yield(curPath.joinToString(""))
                    }
                    // push all children onto the stack
                    for ((childChar, childNode) in curNode.children.entries) {
                        stack.push(Pair(childNode, childChar.toString()))
                    }
                }
            }.iterator()
        }

        fun clear() {
            val stack = Stack<Node>()
            stack.push(this)
            while (stack.isNotEmpty()) {
                val node = stack.pop()
                stack.addAll(node.children.values)
                node.children.clear()
            }
        }

        override fun equals(other: Any?): Boolean {
            return other is Iterable<*> && other.toSet() == toSet()
        }

        override fun hashCode(): Int {
            var result = isTerminal.hashCode()
            result = hashCodePrime * result + children.hashCode()
            result = hashCodePrime * result + numTerminalChildren
            return result
        }
    }

    fun add(value: String): Boolean {
        if (value.contains('\n')) {
            throw IllegalArgumentException("Elements of Trie cannot have newline \\n characters")
        }

        val newElementAdded = root.add(value)
        if (newElementAdded) {
            wordsNumberInRoot += 1
        }
        return newElementAdded
    }

    fun contains(value: String): Boolean {
        return root.contains(value)
    }

    fun remove(value: String): Boolean {
        val elementGotRemoved = root.remove(value)
        if (elementGotRemoved) {
            wordsNumberInRoot -= 1
        }
        return elementGotRemoved
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        return root.howManyStartWithPrefix(prefix)
    }

    fun clear() {
        root.clear()
        wordsNumberInRoot = 0
    }

    fun writeObject(output: OutputStream) {
        val writer = OutputStreamWriter(output)
        for (string in this) {
            writer.write("$string\n")
        }
        writer.flush()
        writer.close()
    }

    fun readObject(input: InputStream) {
        clear()
        val reader = InputStreamReader(input)
        val bufferedReader = BufferedReader(reader)
        bufferedReader.forEachLine {
            add(it)
        }
        bufferedReader.close()
    }

    override fun iterator(): Iterator<String> {
        return root.iterator()
    }

    override fun equals(other: Any?): Boolean {
        return root == other
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }
}
