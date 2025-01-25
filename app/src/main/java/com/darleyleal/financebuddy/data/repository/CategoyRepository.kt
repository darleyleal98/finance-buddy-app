package com.darleyleal.financebuddy.data.repository

import com.darleyleal.financebuddy.data.dao.CategoryDao
import com.darleyleal.financebuddy.data.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoyRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun insert(category: Category) = categoryDao.insert(category)
    suspend fun update(category: Category) = categoryDao.update(category)
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