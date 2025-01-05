package com.darleyleal.financebuddy.presenter.app.di

import android.content.Context
import androidx.room.Room
import com.darleyleal.financebuddy.data.database.AppDatabase
import com.darleyleal.financebuddy.data.database.dao.BalanceDao
import com.darleyleal.financebuddy.data.database.dao.CategoryDao
import com.darleyleal.financebuddy.data.database.dao.RegistrationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideRegistrationDao(appDatabase: AppDatabase): RegistrationDao =
        appDatabase.registrationDao()

    @Singleton
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao = appDatabase.categoryDao()

    @Singleton
    @Provides
    fun provideBalanceDao(appDatabase: AppDatabase): BalanceDao = appDatabase.balanceDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
}