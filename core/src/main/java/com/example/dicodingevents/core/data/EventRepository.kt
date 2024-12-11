package com.example.dicodingevents.core.data

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

class EventRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IEventRepository {

    override fun getAllEvent(): Flow<com.example.dicodingevents.core.data.Resource<List<com.example.dicodingevents.core.domain.model.Event>>> =
        object : NetworkBoundResource<List<com.example.dicodingevents.core.domain.model.Event>, List<EventResponse>>() {
            override fun loadFromDB(): Flow<List<com.example.dicodingevents.core.domain.model.Event>> {
                return localDataSource.getAllEvent().map {
                    com.example.dicodingevents.core.utils.DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<com.example.dicodingevents.core.domain.model.Event>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<EventResponse>>> =
                remoteDataSource.getAllEvent()

            override suspend fun saveCallResult(data: List<EventResponse>) {
                val eventList = com.example.dicodingevents.core.utils.DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEvent(eventList)
            }
        }.asFlow()

    override fun getFavoriteEvent(): Flow<List<com.example.dicodingevents.core.domain.model.Event>> {
        return localDataSource.getFavoriteEvent().map {
            com.example.dicodingevents.core.utils.DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteEvent(event: com.example.dicodingevents.core.domain.model.Event, state: Boolean) {
        val eventEntity = com.example.dicodingevents.core.utils.DataMapper.mapDomainToEntity(event)
        appExecutors.diskIO().execute { localDataSource.setFavoriteEvent(eventEntity, state) }
    }
}

