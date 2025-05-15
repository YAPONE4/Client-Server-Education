package utils

object Validators {
    val EMAIL_REGEX = Regex("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}\$")

    fun isValidEmail(email: String): Boolean {
        return EMAIL_REGEX.matches(email)
    }
}