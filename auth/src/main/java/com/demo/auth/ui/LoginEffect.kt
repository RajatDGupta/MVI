package com.demo.auth.ui

import com.demo.core.common.mvi.UiEffect

/**
 * Presentation Layer (MVI) — One-shot side-effects that are consumed exactly once.
 * Unlike [LoginState], these are not idempotent; they drive navigation or ephemeral UI.
 */
sealed class LoginEffect : UiEffect {

    /** Navigate to the home/dashboard screen after a successful login. */
    data object NavigateToHome : LoginEffect()

    /** Show a toast or snackbar with [message]. */
    data class ShowToast(val message: String) : LoginEffect()
}
