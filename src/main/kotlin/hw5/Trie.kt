package hw5

import java.io.*
import java.lang.IllegalArgumentException
import java.util.*


class Trie : Serializable {

    private var root = Node()
    private var rootSize: Int = 0

    val size: Int
        get() = rootSize


    class Node(private var isTerminal: Boolean = false) {
        private val children = hashMapOf<Char, Node>()
        private var numTerminalChildren: Int = 0

        fun addChild(symbol: Char): Node {
            if (children[symbol] != null) {
                throw IllegalArgumentException("A child for this symbol is already set")
            }
            val newChild = Node()
            children[symbol] = newChild
            return newChild
        }

        fun getChild(symbol: Char): Node? {
            return children[symbol]
        }

        private fun traverseToChild(symbol: Char): Node {
            return children[symbol] ?: return addChild(symbol)
        }

        fun add(value: String, valueIndex: Int = 0): Boolean {
            if (value == "" || valueIndex >= value.length) {
                // if this element already exists
                if (isTerminal) {
                    return false
                }
                // add a new element
                isTerminal = true
                return true
            }
            val curChar = value[valueIndex]
            val nextNode = traverseToChild(curChar)
            val newElementAdded = nextNode.add(value, valueIndex + 1)
            if (newElementAdded) {
                numTerminalChildren += 1
            }
            return newElementAdded
        }

        fun contains(value: String, valueIndex: Int = 0): Boolean {
            val curChar = value[valueIndex]
            val nextNode = children[curChar] ?: return false
            return nextNode.contains(value, valueIndex + 1)
        }

        fun remove(value: String, valueIndex: Int = 0): Boolean {
            val curChar = value[valueIndex]
            val nextNode = children[curChar] ?: return false
            val elementGotRemoved = nextNode.remove(value, valueIndex + 1)
            if (elementGotRemoved) {
                numTerminalChildren -= 1
            }
            return elementGotRemoved
        }

        fun howManyStartWithPrefix(value: String, valueIndex: Int = 0): Int {
            if (value == "") {
                return if (isTerminal) numTerminalChildren + 1 else numTerminalChildren
            }
            val curChar = value[valueIndex]
            val nextNode = children[curChar] ?: return 0
            return nextNode.howManyStartWithPrefix(value, valueIndex + 1)
        }

        fun getAll(): List<String> {
            val valuesResult = mutableListOf<String>()
            val curPath = mutableListOf<Char>()

            fun depthFirstSearch(node: Node) {
                if (node.isTerminal) {
                    val curValue = curPath.joinToString("")
                    valuesResult.add(curValue)
                }
                for ((symbol, child) in node.children) {
                    curPath.add(symbol)
                    depthFirstSearch(child)
                    curPath.removeAt(curPath.size - 1)
                }
            }

            depthFirstSearch(this)
            return valuesResult.toList()
        }
    }

    fun add(value: String): Boolean {
        if (value.contains('\n')) {
            throw IllegalArgumentException("Elements of hw5.Trie cannot have newline \\n characters")
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

    override fun toString(): String {
        return toString(", ")
    }

    fun getItems(): List<String> {
        return root.getAll()
    }

    fun toString(separator: String): String {
        return getItems().joinToString(separator)
    }

    @Throws(IOException::class)
    fun serialize(output: OutputStream) {
        val objectOutputStream = ObjectOutputStream(output)
        serialize(objectOutputStream)
    }

    @Throws(IOException::class)
    fun serialize(output: ObjectOutputStream) {
        val convertedToString = toString("\n")
        output.writeChars(convertedToString)
    }

    @Throws(IOException::class)
    fun deserialize(input: InputStream) {
        val scanner = Scanner(input)

        empty()
        while (scanner.hasNext()) {
            val curString = scanner.next()
            try {
                add(curString)
            } catch (e: IllegalArgumentException) {
                throw IOException("Element $curString is not valid")
            }
        }
    }
}
