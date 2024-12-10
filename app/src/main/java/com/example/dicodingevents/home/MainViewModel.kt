package com.example.dicodingevents.home

import androidx.lifecycle.ViewModel
import com.example.dicodingevents.core.domain.usecase.EventUseCase

class MainViewModel(eventUseCase: EventUseCase) : ViewModel() {

    val event = eventUseCase.getAllEvent()
}