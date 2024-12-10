package com.example.dicodingevents.core.domain.usecase

import com.example.dicodingevents.core.data.Resource
import com.example.dicodingevents.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventUseCase {
    fun getAllEvent(): Flow<Resource<List<Event>>>
    fun getFavoriteEvent(): Flow<List<Event>>
    fun setFavoriteEvent(event: Event, state: Boolean)
}