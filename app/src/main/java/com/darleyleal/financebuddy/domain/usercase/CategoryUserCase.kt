package com.darleyleal.financebuddy.domain.usercase

import com.darleyleal.financebuddy.domain.repository.CategoyRepository
import com.darleyleal.financebuddy.data.models.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUserCase @Inject constructor(
    private val repository: CategoyRepository
) {
    suspend fun insert(name: String, type: String) {
        repository.insert(Category(name = name, type = type))
    }

    suspend fun update(id: Long, name: String, type: String) {
        repository.update(Category(id, name, type))
    }

    suspend fun delete(category: Category) {
        repository.delete(category)
    }

    fun listAllCategories() {
        repository.getAllCategories()
    }

    private fun getCategoryById(id: Long): Flow<Category> {
        return repository.getCategoryById(id)
    }

    fun getAllItemsByCategoryEqualsIncome(): Flow<List<Category>> {
        return repository.getAllItemsByCategoryEqualsIncome()
    }

    fun getAllItemsByCategoryEqualsExpense(): Flow<List<Category>> {
        return repository.getAllItemsByCategoryEqualsExpense()
    }
}