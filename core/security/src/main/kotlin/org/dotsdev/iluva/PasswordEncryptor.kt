package org.dotsdev.iluva

import at.favre.lib.crypto.bcrypt.BCrypt
import java.security.SecureRandom

interface IPasswordEncryptor {
    fun validate(input: String, expected: String): Boolean
    fun encrypt(password: String): String
}

class PasswordEncryptor : IPasswordEncryptor {
    private val letters: String = "abcdefghijklmnopqrstuvwxyz"
    private val uppercaseLetters: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val numbers: String = "0123456789"
    private val special: String = "@#=+!£$%&?"
    private val algorithm = "SHA1PRNG"

    fun generatePassword(
        isWithLetter: Boolean = true,
        isWithUppercase: Boolean = true,
        isWithNumbers: Boolean = true,
        isWithSpecial: Boolean = true,
        length: Int = 8
    ): String {
        var result = ""
        var i = 0

        if (isWithLetter) result += letters
        if (isWithUppercase) result += uppercaseLetters
        if (isWithNumbers) result += numbers
        if (isWithSpecial) result += special

        val random = SecureRandom.getInstance(algorithm)
        val stringBuilder = StringBuilder(length)

        do {
            val randomInt = random.nextInt(result.length)
            stringBuilder.append(result[randomInt])
            i++
        } while (i < length)

        return stringBuilder.toString()
    }

    override fun validate(input: String, expected: String): Boolean =
        BCrypt.verifyer().verifyStrict(input.toCharArray(), expected.toCharArray()).verified

    override fun encrypt(password: String): String = BCrypt.withDefaults().hashToString(11, password.toCharArray())

}