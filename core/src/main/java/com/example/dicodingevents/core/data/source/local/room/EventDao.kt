package com.example.dicodingevents.core.data.source.local.room

import androidx.room.*
import com.example.dicodingevents.core.data.source.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAllEvent(): Flow<List<EventEntity>>

    @Query("SELECT * FROM event where isFavorite = 1")
    fun getFavoriteEvent(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: List<EventEntity>)

    @Update
    fun updateFavoriteEvent(event: EventEntity)
}
