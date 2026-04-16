package com.demo.auth.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


/**
 * Presentation Layer — Top-level stateful composable that wires the ViewModel.
 * Only this function knows about Hilt/ViewModel; all child composables are stateless.
 */
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Consume one-shot effects
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.NavigateToHome    -> onNavigateToHome()
                is LoginEffect.ShowToast         -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    LoginScreenContent(
        state            = state,
        snackbarHostState = snackbarHostState,
        onIntent         = viewModel::onIntent
    )
}

/**
 * Stateless content composable — receives immutable [state] and an [onIntent] lambda.
 * 100 % previewable and testable in isolation.
 */
@Composable
fun LoginScreenContent(
    state: LoginState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onIntent: (LoginIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ── Header ─────────────────────────���───────────────────────
                Text(
                    text  = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text  = "Sign in to continue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(40.dp))

                // ── General error banner ──────────────────────────────────────
                AnimatedVisibility(visible = state.generalError != null) {
                    Text(
                        text  = state.generalError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // ── Email field ───────────────────────────────────────────────
                OutlinedTextField(
                    value         = state.email,
                    onValueChange = { onIntent(LoginIntent.EmailChanged(it)) },
                    label         = { Text("Email") },
                    placeholder   = { Text("you@example.com") },
                    leadingIcon   = { Icon(Icons.Default.Email, contentDescription = null) },
                    isError       = state.emailError != null,
                    supportingText = {
                        if (state.emailError != null) {
                            Text(state.emailError, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction    = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ── Password field ──────────────────��─────────────────────────
                OutlinedTextField(
                    value         = state.password,
                    onValueChange = { onIntent(LoginIntent.PasswordChanged(it)) },
                    label         = { Text("Password") },
                    leadingIcon   = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon  = {
                        val icon = if (state.isPasswordVisible)
                            Icons.Default.VisibilityOff else Icons.Default.Visibility
                        IconButton(onClick = { onIntent(LoginIntent.TogglePasswordVisibility) }) {
                            Icon(icon, contentDescription = "Toggle password visibility")
                        }
                    },
                    visualTransformation = if (state.isPasswordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    isError       = state.passwordError != null,
                    supportingText = {
                        if (state.passwordError != null) {
                            Text(state.passwordError, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction    = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onIntent(LoginIntent.LoginClicked)
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ── Login button ──────────────────────────────────────────────
                Button(
                    onClick  = { onIntent(LoginIntent.LoginClicked) },
                    enabled  = state.isLoginEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Log In", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

// ── Compose Previews ────────────────────────────────────────��─────────────────

@Preview(showBackground = true, name = "Login — Idle")
@Composable
private fun LoginScreenIdlePreview() {
    MaterialTheme {
        LoginScreenContent(state = LoginState(), onIntent = {})
    }
}

@Preview(showBackground = true, name = "Login — Loading")
@Composable
private fun LoginScreenLoadingPreview() {
    MaterialTheme {
        LoginScreenContent(
            state = LoginState(email = "user@test.com", password = "Pass@1234", isLoading = true),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "Login — Validation Errors")
@Composable
private fun LoginScreenErrorPreview() {
    MaterialTheme {
        LoginScreenContent(
            state = LoginState(
                email         = "bad-email",
                password      = "weak",
                emailError    = "Please enter a valid email address",
                passwordError = "Password must be at least 8 characters"
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "Login — General Network Error")
@Composable
private fun LoginScreenNetworkErrorPreview() {
    MaterialTheme {
        LoginScreenContent(
            state = LoginState(
                email        = "user@test.com",
                password     = "Pass@1234",
                generalError = "No internet connection. Please check your network."
            ),
            onIntent = {}
        )
    }
}