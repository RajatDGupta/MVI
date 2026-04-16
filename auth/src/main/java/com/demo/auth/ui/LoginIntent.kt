package com.demo.auth.ui

import com.demo.core.common.mvi.UiIntent

/**
 * Presentation Layer (MVI) — All user intentions/actions are modelled as
 * a sealed hierarchy of Intents. The UI sends these; the ViewModel reduces them.
 */
sealed class LoginIntent : UiIntent {

    /** User typed in the email field. */
    data class EmailChanged(val email: String) : LoginIntent()

    /** User typed in the password field. */
    data class PasswordChanged(val password: String) : LoginIntent()

    /** User tapped the "Login" button. */
    data object LoginClicked : LoginIntent()

    /** User tapped the password visibility toggle. */
    data object TogglePasswordVisibility : LoginIntent()

    /** User dismissed the error snackbar/dialog. */
    data object DismissError : LoginIntent()
}