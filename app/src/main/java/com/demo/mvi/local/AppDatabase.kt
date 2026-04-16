package com.demo.mvi.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.auth.data.dto.UserEntity
import com.demo.auth.data.local.UserDao

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}