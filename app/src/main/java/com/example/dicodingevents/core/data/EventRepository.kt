package com.example.dicodingevents.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.dicodingevents.core.data.source.remote.network.ApiResponse
import com.example.dicodingevents.core.data.source.local.LocalDataSource
import com.example.dicodingevents.core.data.source.remote.RemoteDataSource
import com.example.dicodingevents.core.data.source.remote.response.EventResponse
import com.example.dicodingevents.core.domain.model.Event
import com.example.dicodingevents.core.domain.repository.IEventRepository
import com.example.dicodingevents.core.utils.AppExecutors
import com.example.dicodingevents.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IEventRepository {

    companion object {
        @Volatile
        private var instance: EventRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllEvent(): Flow<Resource<List<Event>>> =
        object : NetworkBoundResource<List<Event>, List<EventResponse>>() {
            override fun loadFromDB(): Flow<List<Event>> {
                return localDataSource.getAllEvent().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Event>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<EventResponse>>> =
                remoteDataSource.getAllEvent()

            override suspend fun saveCallResult(data: List<EventResponse>) {
                val eventList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEvent(eventList)
            }
        }.asFlow()

    override fun getFavoriteEvent(): Flow<List<Event>> {
        return localDataSource.getFavoriteEvent().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteEvent(event: Event, state: Boolean) {
        val eventEntity = DataMapper.mapDomainToEntity(event)
        appExecutors.diskIO().execute { localDataSource.setFavoriteEvent(eventEntity, state) }
    }
}

