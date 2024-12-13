package com.example.dicodingevents.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dicodingevents.core.domain.usecase.EventUseCase

class FavoriteViewModel(eventUseCase: EventUseCase) : ViewModel() {
    val favoriteEvent = eventUseCase.getFavoriteEvent().asLiveData()
}