package com.example.dicodingevents.favorite

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.core.data.EventRepository

class FavoriteViewModel(eventRepository: EventRepository) : ViewModel() {

    val favoriteEvent = eventRepository.getFavoriteEvent()
}