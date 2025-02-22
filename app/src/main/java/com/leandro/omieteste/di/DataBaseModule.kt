package com.leandro.omieteste.di

import android.content.Context
import androidx.room.Room
import com.leandro.omieteste.data.local.AppDatabase
import com.leandro.omieteste.data.local.PedidoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE
        ).build()
    }

    @Provides
    fun providePedidoDao(db: AppDatabase): PedidoDao {
        return db.pedidoDao()
    }
}