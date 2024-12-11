package com.example.dicodingevents.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.dicodingevents.core.data.Resource
import com.example.dicodingevents.core.domain.model.Event
import com.example.dicodingevents.core.domain.repository.IEventRepository

class EventInteractor(private val eventRepository: IEventRepository): EventUseCase {
    override fun getAllEvent() = eventRepository.getAllEvent()

    override fun getFavoriteEvent() = eventRepository.getFavoriteEvent()

    override fun setFavoriteEvent(event: com.example.dicodingevents.core.domain.model.Event, state: Boolean) = eventRepository.setFavoriteEvent(event, state)

}