package com.darleyleal.financebuddy.domain.usercase

import com.darleyleal.financebuddy.data.repository.BalanceRepository
import com.darleyleal.financebuddy.data.models.Balance
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BalanceUserCase @Inject constructor(
    private val repository: BalanceRepository,
) {
    suspend fun updateOrInsertBalance(newValue: String) {
        val balance = repository.getBalance().first()

        when {
            balance != null -> {
                repository.update(
                    balance.copy(
                        availableBalance = newValue.replace(",", ".").toFloat()
                    )
                )
            }

            else -> {
                repository.insert(
                    Balance(
                        availableBalance = newValue.replace(
                            ",", "."
                        ).toFloat()
                    )
                )
            }
        }
    }

    fun getBalance() = repository.getBalance()
}