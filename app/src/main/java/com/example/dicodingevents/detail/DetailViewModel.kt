package com.example.dicodingevents.detail

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.core.data.EventRepository
import com.example.dicodingevents.core.domain.model.Event

class DetailViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun setFavoriteEvent(event: Event, newStatus: Boolean) = eventRepository.setFavoriteTourism(event, newStatus)
}