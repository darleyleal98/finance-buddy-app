package com.darleyleal.financebuddy.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.darleyleal.financebuddy.data.models.Balance
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(balance: Balance)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(balance: Balance)

    @Query("SELECT * FROM balance")
    fun getBalance(): Flow<Balance>
}