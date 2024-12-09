package com.example.dicodingevents.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dicodingevents.core.data.source.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getAllEvent(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event where isFavorite = 1")
    fun getFavoriteEvent(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: List<EventEntity>)

    @Update
    fun updateFavoriteEvent(event: EventEntity)
}
