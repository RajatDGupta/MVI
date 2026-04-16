package com.demo.auth.data.repository

import com.demo.auth.data.dto.UserEntity
import com.demo.auth.data.local.UserDao
import com.demo.auth.domain.model.AuthResult
import com.demo.auth.domain.repository.AuthRepository
import com.demo.core.data.local.datastore.PrefKeys
import com.demo.core.data.local.datastore.PreferenceDataStore
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(val preferences: PreferenceDataStore, val userDao: UserDao) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        preferences.put(PrefKeys.IS_LOGGED_IN,true)
        userDao.insertUser(UserEntity(id = 1, email = email))
        return AuthResult.Success(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "1", email);
    }

}