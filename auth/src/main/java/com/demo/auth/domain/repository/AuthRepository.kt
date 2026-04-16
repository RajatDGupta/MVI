package com.demo.auth.domain.repository

import com.demo.auth.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
}