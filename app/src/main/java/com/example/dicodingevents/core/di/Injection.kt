package com.example.dicodingevents.core.di

import android.content.Context
import com.example.dicodingevents.core.data.source.local.LocalDataSource
import com.example.dicodingevents.core.data.source.local.room.EventDatabase
import com.example.dicodingevents.core.data.EventRepository
import com.example.dicodingevents.core.data.source.remote.RemoteDataSource
import com.example.dicodingevents.core.data.source.remote.network.ApiConfig
import com.example.dicodingevents.core.domain.repository.IEventRepository
import com.example.dicodingevents.core.domain.usecase.EventInteractor
import com.example.dicodingevents.core.domain.usecase.EventUseCase
import com.example.dicodingevents.core.utils.AppExecutors
import com.example.dicodingevents.core.utils.JsonHelper

object Injection {
    private fun provideRepository(context: Context): IEventRepository {
        val database = EventDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.eventDao())
        val appExecutors = AppExecutors()

        return EventRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideEventUseCase(context: Context): EventUseCase {
        val repository = provideRepository(context)
        return EventInteractor(repository)
    }
}
