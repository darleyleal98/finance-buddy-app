package com.darleyleal.financebuddy.data.database.repository

import com.darleyleal.financebuddy.data.database.dao.CategoryDao
import com.darleyleal.financebuddy.data.local.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoyRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun insert(category: Category) = categoryDao.insert(category)

    suspend fun update(id: Long, name: String) {
        categoryDao.update(id, name)
    }

    suspend fun delete(category: Category) = categoryDao.delete(category)

    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().flowOn(Dispatchers.IO).conflate()
    }

    fun getCategoryById(id: Long): Flow<Category> {
        return categoryDao.getCategoryById(id).flowOn(Dispatchers.IO).conflate()
    }

    fun getAllItemsByCategoryEqualsIncome(): Flow<List<Category>> {
        return categoryDao.getAllItemsByCategoryEqualsIncome()
    }

    fun getAllItemsByCategoryEqualsExpense(): Flow<List<Category>> {
        return categoryDao.getAllItemsByCategoryEqualsExpense()
    }
}