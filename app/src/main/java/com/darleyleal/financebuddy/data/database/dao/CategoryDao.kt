package com.darleyleal.financebuddy.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.darleyleal.financebuddy.data.local.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Long): Flow<Category>

    @Query("SELECT * FROM categories WHERE type = 'Income'")
    fun getAllItemsByCategoryEqualsIncome(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE type = 'Expense'")
    fun getAllItemsByCategoryEqualsExpense(): Flow<List<Category>>
}