package com.example.dicodingevents.core.di

import android.content.Context

import com.example.dicodingevents.core.data.source.local.LocalDataSource
import com.example.dicodingevents.core.data.source.local.room.EventDatabase

import com.example.dicodingevents.core.data.EventRepository
import com.example.dicodingevents.core.data.source.remote.RemoteDataSource
import com.example.dicodingevents.core.utils.AppExecutors
import com.example.dicodingevents.core.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val database = EventDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.eventDao())
        val appExecutors = AppExecutors()

        return EventRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
