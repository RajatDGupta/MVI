package com.demo.auth.domain.usecase

import com.demo.auth.domain.model.AuthResult
import com.demo.auth.domain.repository.AuthRepository
import com.demo.auth.domain.validation.LoginValidator
import javax.inject.Inject

/**
 * Domain Layer — LoginUseCase encapsulates the business rule:
 *   1. Validate inputs locally.
 *   2. Delegate to [AuthRepository] for network authentication.
 *
 * Single-responsibility: knows *what* to do, not *how* it is done.
 */
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val validator: LoginValidator
) {

    suspend operator fun invoke(email: String, password: String): AuthResult {
        validator.validateEmail(email)?.let { return AuthResult.Error(it) }
        validator.validatePassword(password)?.let { return AuthResult.Error(it) }

        return repository.login(email.trim(), password)
    }
}