package com.demo.auth.data.dto

import androidx.room.Entity

@Entity(tableName = "users")
data class UserEntity(
    val id: Int = 0,
    val email: String
)