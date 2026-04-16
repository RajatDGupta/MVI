package com.demo.auth.ui

import com.demo.core.common.mvi.UiState

/**
 * Presentation Layer (MVI) — Immutable snapshot of the login UI at any point in time.
 * The UI renders itself purely from this state; it never holds local mutable state.
 */
data class LoginState(
    val email: String             = "",
    val password: String          = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean        = false,
    val emailError: String?       = null,
    val passwordError: String?    = null,
    val generalError: String?     = null
) : UiState {
    /** Convenience: the form is submittable only when both fields are non-blank. */
    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}