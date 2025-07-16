package com.darleyleal.financebuddy.data.repository

import com.darleyleal.financebuddy.data.dao.RegistrationDao
import com.darleyleal.financebuddy.data.models.Registration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val dao: RegistrationDao
) {
    suspend fun insert(registration: Registration) = dao.insert(registration)
    suspend fun update(registration: Registration) = dao.update(registration)
    suspend fun delete(registration: Registration) = dao.delete(registration)

    fun getAllRegistrations(): Flow<List<Registration>> {
        return dao.getAllRegistrations().flowOn(Dispatchers.IO).conflate()
    }

    fun getRegistrationById(id: Long): Flow<Registration> {
        return dao.getRegistrationById(id).flowOn(Dispatchers.IO).conflate()
    }

    fun getAllItemsByRegistrationEqualsIncome(): Flow<List<Registration>> {
        return dao.getAllRegistrationEqualsIncome().flowOn(Dispatchers.IO).conflate()
    }

    fun getAllItemsByRegistrationEqualsExpense(): Flow<List<Registration>> {
        return dao.getAllRegistrationEqualsExpense().flowOn(Dispatchers.IO).conflate()
    }
}