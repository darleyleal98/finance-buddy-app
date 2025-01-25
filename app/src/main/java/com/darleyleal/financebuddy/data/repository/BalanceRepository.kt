package com.darleyleal.financebuddy.data.repository

import com.darleyleal.financebuddy.data.dao.BalanceDao
import com.darleyleal.financebuddy.data.models.Balance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BalanceRepository @Inject constructor(
    private val balanceDao: BalanceDao
) {
    suspend fun insert(balance: Balance) = balanceDao.insert(balance)
    suspend fun update(balance: Balance) = balanceDao.update(balance)
    fun getBalance() = balanceDao.getBalance().flowOn(Dispatchers.IO).conflate()
}