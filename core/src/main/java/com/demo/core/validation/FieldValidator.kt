package com.demo.core.validation

/**
 * Core Validation — Generic interface for field validators.
 *
 * Each validator receives a [value] and returns either a human-readable
 * error [String] or **null** when the value is valid.
 *
 * @param T The type of the value being validated (usually [String]).
 */
fun interface FieldValidator<T> {
    /** Returns an error message, or null if [value] is valid. */
    fun validate(value: T): String?
}

