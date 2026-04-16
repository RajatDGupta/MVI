package com.demo.core.validation

/**
 * Core Validation — Reusable email format validator.
 *
 * Returns a human-readable error message or null when the email is valid.
 * Extracted from the feature-specific LoginValidator so it can be reused
 * across any future feature that requires an email field.
 */
object EmailValidator : FieldValidator<String> {

    private val EMAIL_REGEX = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    )

    override fun validate(value: String): String? = when {
        value.isBlank()                        -> "Email must not be empty"
        !EMAIL_REGEX.matches(value.trim())     -> "Please enter a valid email address"
        else                                   -> null
    }
}

