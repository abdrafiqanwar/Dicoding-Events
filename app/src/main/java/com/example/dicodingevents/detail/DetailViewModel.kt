package com.example.dicodingevents.detail

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.core.data.EventRepository
import com.example.dicodingevents.core.data.source.local.entity.EventEntity

class DetailViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun setFavoriteEvent(event: EventEntity, newStatus: Boolean) = eventRepository.setFavoriteTourism(event, newStatus)
}