package com.demo.auth.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.auth.data.dto.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity>
}