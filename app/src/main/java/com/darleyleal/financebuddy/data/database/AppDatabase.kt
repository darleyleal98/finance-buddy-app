package com.darleyleal.financebuddy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darleyleal.financebuddy.data.database.dao.CategoryDao
import com.darleyleal.financebuddy.data.database.dao.RegistrationDao
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.data.local.Registration

@Database(
    entities = [Registration::class, Category::class],
    version = 2, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
    abstract fun categoryDao(): CategoryDao
}