package homeworks.hw5

import java.io.InputStream
import java.io.OutputStream

interface CustomSerializable {
    fun serialize(output: OutputStream)
    fun deserialize(input: InputStream)
}
