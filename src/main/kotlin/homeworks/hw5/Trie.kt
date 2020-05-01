package homeworks.hw5

import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.lang.IllegalArgumentException
import java.util.Stack

class Trie : CustomSerializable, Iterable<String> {
    private var root = Node()
    private var rootSize: Int = 0

    val size: Int
        get() = rootSize

    class Node(private var isTerminal: Boolean = false) : Iterable<String> {
        private val children = hashMapOf<Char, Node>()
        private var numTerminalChildren: Int = 0

        private fun getChild(symbol: Char, createNewIfDoesNotExist: Boolean = false): Node? {
            if (children[symbol] == null && createNewIfDoesNotExist) {
                children[symbol] = Node()
            }
            return children[symbol]
        }

        private fun traverse(value: String, build: Boolean = false): Pair<List<Node>, Boolean> {
            val path = mutableListOf<Node>()
            var curNode = this
            path.add(curNode)

            for (char in value) {
                val nextNode = curNode.getChild(char, build) ?: return Pair(path, false)
                curNode = nextNode
                path.add(curNode)
            }
            return Pair(path, true)
        }

        fun add(value: String): Boolean {
            val (path) = traverse(value, true)
            val isThisValueNew = !path.last().isTerminal
            if (isThisValueNew) {
                path.last().isTerminal = true
                for (node in path.asReversed().drop(1)) {
                    node.numTerminalChildren += 1
                }
            }
            return isThisValueNew
        }

        fun contains(value: String): Boolean {
            val (path, pathIsComplete) = traverse(value)
            return pathIsComplete && path.last().isTerminal
        }

        fun remove(value: String): Boolean {
            val (path, pathIsComplete) = traverse(value)
            val isValueInTrie = pathIsComplete && path.last().isTerminal
            if (isValueInTrie) {
                path.last().isTerminal = false
                for (node in path.asReversed().drop(1)) {
                    node.numTerminalChildren -= 1
                    if (node.numTerminalChildren == 0) {
                        node.children.clear()
                    }
                }
            }
            return isValueInTrie
        }

        fun howManyStartWithPrefix(value: String): Int {
            val (path, pathIsComplete) = traverse(value)
            val lastNode = path.last()
            return if (!pathIsComplete) {
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

        override fun equals(other: Any?): Boolean {
            return when (other) {
                is Iterable<*> -> toSet() == other.toSet()
                else -> false
            }
        }

        override fun hashCode(): Int {
            var result = isTerminal.hashCode()
            result = 31 * result + children.hashCode()
            result = 31 * result + numTerminalChildren
            return result
        }
    }

    fun add(value: String): Boolean {
        if (value.contains('\n')) {
            throw IllegalArgumentException("Elements of Trie cannot have newline \\n characters")
        }

        val newElementAdded = root.add(value)
        if (newElementAdded) {
            rootSize += 1
        }
        return newElementAdded
    }

    fun contains(value: String): Boolean {
        return root.contains(value)
    }

    fun remove(value: String): Boolean {
        val elementGotRemoved = root.remove(value)
        if (elementGotRemoved) {
            rootSize -= 1
        }
        return elementGotRemoved
    }

    fun howManyStartWithPrefix(prefix: String): Int {
        return root.howManyStartWithPrefix(prefix)
    }

    fun empty() {
        root = Node()
        rootSize = 0
    }

    override fun serialize(output: OutputStream) {
        val writer = OutputStreamWriter(output)
        for (string in this) {
            writer.write("$string\n")
        }
        writer.flush()
        writer.close()
    }

    override fun deserialize(input: InputStream) {
        empty()
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
