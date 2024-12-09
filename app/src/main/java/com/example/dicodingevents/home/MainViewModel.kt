package com.example.dicodingevents.home

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.core.data.EventRepository

class MainViewModel(eventRepository: EventRepository) : ViewModel() {

    val event = eventRepository.getAllEvent()
}