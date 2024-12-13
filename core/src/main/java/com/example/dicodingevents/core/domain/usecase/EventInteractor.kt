package com.example.dicodingevents.core.domain.usecase

import com.example.dicodingevents.core.domain.model.Event
import com.example.dicodingevents.core.domain.repository.IEventRepository

class EventInteractor(private val eventRepository: IEventRepository): EventUseCase {
    override fun getAllEvent() = eventRepository.getAllEvent()

    override fun getFavoriteEvent() = eventRepository.getFavoriteEvent()

    override fun setFavoriteEvent(event: Event, state: Boolean) = eventRepository.setFavoriteEvent(event, state)

}