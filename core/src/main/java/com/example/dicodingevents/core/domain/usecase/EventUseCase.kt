package com.example.dicodingevents.core.domain.usecase

import com.example.dicodingevents.core.data.Resource
import com.example.dicodingevents.core.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventUseCase {
    fun getAllEvent(): Flow<com.example.dicodingevents.core.data.Resource<List<com.example.dicodingevents.core.domain.model.Event>>>
    fun getFavoriteEvent(): Flow<List<com.example.dicodingevents.core.domain.model.Event>>
    fun setFavoriteEvent(event: com.example.dicodingevents.core.domain.model.Event, state: Boolean)
}