package com.demo.auth.domain.model

/**
 * Domain Layer — Sealed result type that represents every possible
 * outcome of an authentication operation without leaking data-layer types.
 */
sealed class AuthResult {

    /** Login succeeded; carries the persisted session details. */
    data class Success(
        val accessToken: String,
        val refreshToken: String,
        val userId: String,
        val email: String
    ) : AuthResult()

    /** Login failed; carries a human-readable [message]. */
    data class Error(val message: String) : AuthResult()
}
