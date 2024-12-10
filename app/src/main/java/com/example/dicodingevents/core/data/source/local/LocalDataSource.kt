package com.example.dicodingevents.core.data.source.local

import androidx.lifecycle.LiveData
import com.example.dicodingevents.core.data.source.local.entity.EventEntity
import com.example.dicodingevents.core.data.source.local.room.EventDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val eventDao: EventDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(eventDao: EventDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(eventDao)
            }
    }

    fun getAllEvent(): Flow<List<EventEntity>> = eventDao.getAllEvent()

    fun getFavoriteEvent(): Flow<List<EventEntity>> = eventDao.getFavoriteEvent()

    suspend fun insertEvent(eventList: List<EventEntity>) = eventDao.insertEvent(eventList)

    fun setFavoriteEvent(event: EventEntity, newState: Boolean) {
        event.isFavorite = newState
        eventDao.updateFavoriteEvent(event)
    }
}