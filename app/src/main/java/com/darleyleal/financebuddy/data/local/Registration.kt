package com.darleyleal.financebuddy.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registrations")
data class Registration(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "value")
    val value: Float,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "type")
    val type: String
)