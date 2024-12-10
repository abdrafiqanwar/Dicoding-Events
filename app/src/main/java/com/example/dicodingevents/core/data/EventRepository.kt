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

    override fun getAllEvent(): LiveData<Resource<List<Event>>> =
        object : NetworkBoundResource<List<Event>, List<EventResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Event>> {
                return localDataSource.getAllEvent().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Event>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<EventResponse>>> =
                remoteDataSource.getAllEvent()

            override fun saveCallResult(data: List<EventResponse>) {
                val eventList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEvent(eventList)
            }
        }.asLiveData()

    override fun getFavoriteEvent(): LiveData<List<Event>> {
        return localDataSource.getFavoriteEvent().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteEvent(event: Event, state: Boolean) {
        val eventEntity = DataMapper.mapDomainToEntity(event)
        appExecutors.diskIO().execute { localDataSource.setFavoriteEvent(eventEntity, state) }
    }
}

