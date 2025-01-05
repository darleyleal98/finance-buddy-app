package com.darleyleal.financebuddy.data.database.repository

import androidx.room.util.copy
import com.darleyleal.financebuddy.data.database.dao.BalanceDao
import com.darleyleal.financebuddy.data.local.Balance
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