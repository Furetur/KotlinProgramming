package homeworks.hw1

fun isPalindrome(str: String): Boolean {
    return str.reversed() == str
}

fun main() {
    println("This checks whether a string is a palindrome. Please enter the string:")

    val inputString = readLine()

    if (inputString == null) {
        println("Received a null string")
        return
    }

    if (isPalindrome(inputString)) {
        println("Your string IS a palindrome")
    } else {
        println("Your string is NOT a palindrome")
    }
}
