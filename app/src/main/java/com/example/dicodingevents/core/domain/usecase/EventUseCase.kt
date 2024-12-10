package com.example.dicodingevents.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.dicodingevents.core.data.Resource
import com.example.dicodingevents.core.domain.model.Event

interface EventUseCase {
    fun getAllEvent(): LiveData<Resource<List<Event>>>
    fun getFavoriteEvent(): LiveData<List<Event>>
    fun setFavoriteEvent(event: Event, state: Boolean)
}