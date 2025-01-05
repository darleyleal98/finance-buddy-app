package com.darleyleal.financebuddy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darleyleal.financebuddy.data.database.dao.BalanceDao
import com.darleyleal.financebuddy.data.database.dao.CategoryDao
import com.darleyleal.financebuddy.data.database.dao.RegistrationDao
import com.darleyleal.financebuddy.data.local.Balance
import com.darleyleal.financebuddy.data.local.Category
import com.darleyleal.financebuddy.data.local.Registration

@Database(
    entities = [Registration::class, Category::class, Balance::class],
    version = 4, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
    abstract fun categoryDao(): CategoryDao
    abstract fun balanceDao(): BalanceDao
}