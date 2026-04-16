package com.demo.auth.domain.validation


import com.demo.core.validation.EmailValidator
import com.demo.core.validation.PasswordValidator
import javax.inject.Inject

/**
 * Domain Layer — Composes core validators for login-specific field validation.
 *
 * Delegates to [EmailValidator] and [PasswordValidator] from :core so that
 * the validation logic is centralised and reusable across features.
 * Returns a human-readable error string or null when the value is valid.
 */
class LoginValidator @Inject constructor() {

    /** Returns an error message if [email] is invalid, or null if valid. */
    fun validateEmail(email: String): String? = EmailValidator.validate(email)

    /** Returns an error message if [password] is invalid, or null if valid. */
    fun validatePassword(password: String): String? = PasswordValidator.validate(password)
}

