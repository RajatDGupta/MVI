package com.demo.core.validation

/**
 * Core Validation — Reusable password strength validator.
 *
 * Rules enforced:
 *  - At least 8 characters
 *  - At least one uppercase letter
 *  - At least one digit
 *  - At least one special character (non-alphanumeric)
 */
object PasswordValidator : FieldValidator<String> {

    override fun validate(value: String): String? = when {
        value.isBlank()                      -> "Password must not be empty"
        value.length < 8                     -> "Password must be at least 8 characters"
        !value.any { it.isUpperCase() }      -> "Password must contain at least one uppercase letter"
        !value.any { it.isDigit() }          -> "Password must contain at least one digit"
        !value.any { !it.isLetterOrDigit() } -> "Password must contain at least one special character"
        else                                 -> null
    }
}

