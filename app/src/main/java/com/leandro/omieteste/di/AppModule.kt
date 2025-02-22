package com.leandro.omieteste.di

import com.leandro.omieteste.data.local.PedidoDao
import com.leandro.omieteste.data.repository.PedidoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePedidoRepository(pedidoDao: PedidoDao): PedidoRepository {
        return PedidoRepository(pedidoDao)
    }
}