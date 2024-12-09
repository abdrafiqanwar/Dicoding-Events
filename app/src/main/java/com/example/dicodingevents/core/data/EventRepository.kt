package com.example.dicodingevents.core.data

import androidx.lifecycle.LiveData
import com.example.dicodingevents.core.data.source.remote.network.ApiResponse
import com.example.dicodingevents.core.data.source.local.LocalDataSource
import com.example.dicodingevents.core.data.source.local.entity.EventEntity
import com.example.dicodingevents.core.data.source.remote.RemoteDataSource
import com.example.dicodingevents.core.data.source.remote.response.EventResponse
import com.example.dicodingevents.core.utils.AppExecutors
import com.example.dicodingevents.core.utils.DataMapper

class EventRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) {

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

    fun getAllEvent(): LiveData<Resource<List<EventEntity>>> =
        object : NetworkBoundResource<List<EventEntity>, List<EventResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<EventEntity>> {
                return localDataSource.getAllEvent()
            }

            override fun shouldFetch(data: List<EventEntity>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<EventResponse>>> =
                remoteDataSource.getAllEvent()

            override fun saveCallResult(data: List<EventResponse>) {
                val eventList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEvent(eventList)
            }
        }.asLiveData()

    fun getFavoriteEvent(): LiveData<List<EventEntity>> {
        return localDataSource.getFavoriteEvent()
    }

    fun setFavoriteTourism(event: EventEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteEvent(event, state) }
    }
}

