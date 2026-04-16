package com.demo.auth.ui

import androidx.lifecycle.viewModelScope
import com.demo.auth.domain.model.AuthResult
import com.demo.auth.domain.usecase.LoginUseCase
import com.demo.core.common.dispatcher.DispatcherProvider
import com.demo.core.common.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Presentation Layer (MVI ViewModel) — Owns the [LoginState] and processes every
 * [LoginIntent] through a pure reducer, emitting one-shot [LoginEffect]s via a
 * Channel to guarantee they are delivered exactly once.
 *
 * Extends [BaseViewModel] from :core — all MVI wiring (StateFlow, Channel,
 * setState, sendEffect) is handled there.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dispatchers: DispatcherProvider
) : BaseViewModel<LoginState, LoginIntent, LoginEffect>(LoginState()) {

    // ── Intent handler ────────────────────────────────────────────────────
    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged             -> reduceEmailChanged(intent.email)
            is LoginIntent.PasswordChanged          -> reducePasswordChanged(intent.password)
            is LoginIntent.TogglePasswordVisibility -> reduceTogglePassword()
            is LoginIntent.LoginClicked             -> reduceLoginClicked()
            is LoginIntent.DismissError             -> reduceDismissError()
        }
    }

    // ── Reducers ──────────────────────────────────────────────────────────────

    private fun reduceEmailChanged(email: String) {
        setState { copy(email = email, emailError = null, generalError = null) }
    }

    private fun reducePasswordChanged(password: String) {
        setState { copy(password = password, passwordError = null, generalError = null) }
    }

    private fun reduceTogglePassword() {
        setState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun reduceDismissError() {
        setState { copy(generalError = null, emailError = null, passwordError = null) }
    }

    private fun reduceLoginClicked() {
        if (currentState.isLoading) return   // guard against double-tap

        viewModelScope.launch(dispatchers.io) {
            setState { copy(isLoading = true, emailError = null, passwordError = null, generalError = null) }

            when (val result = loginUseCase(currentState.email, currentState.password)) {
                is AuthResult.Success -> {
                    setState { copy(isLoading = false) }
                    sendEffect(LoginEffect.NavigateToHome)
                }
                is AuthResult.Error -> {
                    val emailError    = result.message.takeIf { isEmailError(it) }
                    val passwordError = result.message.takeIf { isPasswordError(it) }
                    val generalError  = result.message.takeUnless { isEmailError(it) || isPasswordError(it) }

                    setState {
                        copy(
                            isLoading     = false,
                            emailError    = emailError,
                            passwordError = passwordError,
                            generalError  = generalError
                        )
                    }
                }
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private fun isEmailError(message: String) =
        message.contains("email", ignoreCase = true)

    private fun isPasswordError(message: String) =
        message.contains("password", ignoreCase = true)
}