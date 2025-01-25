package com.darleyleal.financebuddy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darleyleal.financebuddy.data.dao.BalanceDao
import com.darleyleal.financebuddy.data.dao.CategoryDao
import com.darleyleal.financebuddy.data.dao.RegistrationDao
import com.darleyleal.financebuddy.data.models.Balance
import com.darleyleal.financebuddy.data.models.Category
import com.darleyleal.financebuddy.data.models.Registration

@Database(
    entities = [Registration::class, Category::class, Balance::class],
    version = 8, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
    abstract fun categoryDao(): CategoryDao
    abstract fun balanceDao(): BalanceDao
}