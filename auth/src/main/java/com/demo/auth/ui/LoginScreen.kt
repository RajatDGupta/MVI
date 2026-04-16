package com.demo.auth.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Text("Login Screen")
}