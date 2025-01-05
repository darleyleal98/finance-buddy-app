package com.darleyleal.financebuddy.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.darleyleal.financebuddy.data.local.Registration
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistrationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registration: Registration)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(registration: Registration)

    @Delete
    suspend fun delete(registration: Registration)

    @Query("SELECT * FROM registrations WHERE id = :id")
    fun getResgistationById(id: Long) : Flow<Registration>

    @Query("SELECT * FROM registrations")
    fun getAllRegistrations(): Flow<List<Registration>>

    @Query("SELECT * FROM registrations WHERE type = 'Income'")
    fun getAllRegistrationEqualsIncome(): Flow<List<Registration>>

    @Query("SELECT * FROM registrations WHERE type = 'Expense'")
    fun getAllRegistrationEqualsExpense(): Flow<List<Registration>>
}