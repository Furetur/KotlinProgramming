package homeworks.hw1

import homeworks.hw1.isPalindrome
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class TaskTest {

    @Test
    fun `should consider empty strings palindromes`() {
        assert(isPalindrome(""))
    }

    @Test
    fun `should consider strings of length 1 palindromes`() {
        assert(isPalindrome("a"))
    }

    @Test
    fun `should work with long palindromes`() {
        assert(isPalindrome("1234567890a09876543211234567890a09876543211234567890a09876543211234567890a0987654321"))
    }

    @Test
    fun `should work with strings that contain null characters 1`() {
        val charArray: CharArray = charArrayOf('a', '\u0000', 'a')
        val string = charArray.fold("") {finalString: String, curChar: Char -> finalString + curChar}
        assert(isPalindrome(string))
    }

    @Test
    fun `should work with strings that contain null characters 2`() {
        val charArray: CharArray = charArrayOf('a', '\u0000', 'a', 'a')
        val string = charArray.fold("") {finalString: String, curChar: Char -> finalString + curChar}
        assertFalse(isPalindrome(string))
    }

    @Test
    fun `should work with emotes`() {
        assert(isPalindrome("\uD83D\uDE02ala\uD83D\uDE02"))
    }

    @Test
    fun `should detect simple palindromes`() {
        assert(isPalindrome("qwertyuiop[]][poiuytrewq"))
    }

    @Test
    fun `should discard simple non-palindromes`() {
        assertFalse(isPalindrome("111111111111110"))
    }

    @Test
    fun `should discard long non-palindromes`() {
        assertFalse(isPalindrome("111111111111110111111111111110111111111111110111111111111110111111111111110"))
    }

    @Test
    fun `should discard long non-palindromes 2`() {
        val testString = "Gerard (died 1108) was Archbishop of York between 1100 and 1108 and Lord Chancellor of England from 1085 until 1092. A Norman, he was a member of the cathedral clergy at Rouen before becoming a royal clerk under King William I of England, who appointed him Lord Chancellor. He continued in that office under King William II Rufus, who rewarded him with the Bishopric of Hereford in 1096. Soon after Henry I's coronation, Gerard was appointed to the recently vacant see of York, and became embroiled in the dispute between York and the see of Canterbury concerning which archbishopric had primacy over England. He secured papal recognition of York's jurisdiction over the church in Scotland but was forced to accept Canterbury's authority over York. He also worked on reconciling the Investiture Controversy between the king and the papacy over the right to appoint bishops until the controversy's resolution in 1107. Because of rumours, as a student of astrology, that he was a magician and a sorcerer, and also because of his unpopular attempts to reform his clergy, he was denied a burial inside York Minster but his remains were later moved into the cathedral. (Full article...)"
        assertFalse(isPalindrome(testString))
    }

    @Test
    fun `should detect long palindromes`() {
        val string = "Gerard (died 1108) was Archbishop of York between 1100 and 1108 and Lord Chancellor of England from 1085 until 1092. A Norman, he was a member of the cathedral clergy at Rouen before becoming a royal clerk under King William I of England, who appointed him Lord Chancellor. He continued in that office under King William II Rufus, who rewarded him with the Bishopric of Hereford in 1096. Soon after Henry I's coronation, Gerard was appointed to the recently vacant see of York, and became embroiled in the dispute between York and the see of Canterbury concerning which archbishopric had primacy over England. He secured papal recognition of York's jurisdiction over the church in Scotland but was forced to accept Canterbury's authority over York. He also worked on reconciling the Investiture Controversy between the king and the papacy over the right to appoint bishops until the controversy's resolution in 1107. Because of rumours, as a student of astrology, that he was a magician and a sorcerer, and also because of his unpopular attempts to reform his clergy, he was denied a burial inside York Minster but his remains were later moved into the cathedral. (Full article...)"
        val testString = string + string.reversed()
        assert(isPalindrome(testString))
    }
}
