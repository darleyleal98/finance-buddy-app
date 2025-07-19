package com.darleyleal.financebuddy.domain.usercase

import com.darleyleal.financebuddy.domain.repository.RegistrationRepository
import com.darleyleal.financebuddy.data.models.Registration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUserCase @Inject constructor(private val repository: RegistrationRepository) {
    suspend fun insert(
        name: String, description: String, value: String,
        date: String, type: String, category: String
    ) {
        repository.insert(
            Registration(
                name = name,
                description = description,
                value = value.replace(",", ".").toFloat(),
                date = date,
                type = type,
                category = category
            )
        )
    }

    suspend fun update(
        id: Long, name: String, description: String,
        value: String, date: String, category: String, type: String
    ) {
        repository.update(
            Registration(
                id = id,
                name = name,
                description = description,
                value = value.replace(",", ".").toFloat(),
                date = date,
                category = category,
                type = type
            )
        )
    }

    suspend fun delete(registration: Registration) {
        repository.delete(registration)
    }

    fun getAllRegistrations(): Flow<List<Registration>> {
        return repository.getAllRegistrations()
    }

    fun getRegistrationById(id: Long): Flow<Registration> {
        return repository.getRegistrationById(id)
    }

    fun getAllRegistrationEqualsIncomes(): Flow<List<Registration>> {
        return repository.getAllItemsByRegistrationEqualsIncome()
    }

    fun getAllRegistrationEqualsExpense(): Flow<List<Registration>> {
        return repository.getAllItemsByRegistrationEqualsExpense()
    }
}