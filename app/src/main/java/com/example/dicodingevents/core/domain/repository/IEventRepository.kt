package com.example.dicodingevents.core.domain.repository

import androidx.lifecycle.LiveData
import com.example.dicodingevents.core.data.Resource
import com.example.dicodingevents.core.domain.model.Event

interface IEventRepository {

    fun getAllEvent(): LiveData<Resource<List<Event>>>

    fun getFavoriteEvent(): LiveData<List<Event>>

    fun setFavoriteEvent(event: Event, state: Boolean)
}