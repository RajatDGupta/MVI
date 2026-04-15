package com.demo.mvi.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.auth.data.UserEntity
import com.demo.auth.domain.UserDao

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}