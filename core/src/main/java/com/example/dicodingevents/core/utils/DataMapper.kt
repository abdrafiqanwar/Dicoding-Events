package com.example.dicodingevents.core.utils

import com.example.dicodingevents.core.data.source.local.entity.EventEntity
import com.example.dicodingevents.core.data.source.remote.response.EventResponse
import com.example.dicodingevents.core.domain.model.Event

object DataMapper {
    fun mapResponsesToEntities(input: List<EventResponse>): List<EventEntity> {
        val eventList = ArrayList<EventEntity>()
        input.map {
            val event = EventEntity(
                id = it.id,
                summary = it.summary,
                mediaCover = it.mediaCover,
                registrants = it.registrants,
                imageLogo = it.imageLogo,
                link = it.link,
                description = it.description,
                ownerName = it.ownerName,
                cityName = it.cityName,
                quota = it.quota,
                name = it.name,
                beginTime = it.beginTime,
                endTime = it.endTime,
                category = it.category
            )
            eventList.add(event)
        }
        return eventList
    }

    fun mapEntitiesToDomain(input: List<EventEntity>): List<com.example.dicodingevents.core.domain.model.Event> =
        input.map {
            com.example.dicodingevents.core.domain.model.Event(
                id = it.id,
                summary = it.summary,
                mediaCover = it.mediaCover,
                registrants = it.registrants,
                imageLogo = it.imageLogo,
                link = it.link,
                description = it.description,
                ownerName = it.ownerName,
                cityName = it.cityName,
                quota = it.quota,
                name = it.name,
                beginTime = it.beginTime,
                endTime = it.endTime,
                category = it.category,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: com.example.dicodingevents.core.domain.model.Event) = EventEntity(
        id = input.id,
        summary = input.summary,
        mediaCover = input.mediaCover,
        registrants = input.registrants,
        imageLogo = input.imageLogo,
        link = input.link,
        description = input.description,
        ownerName = input.ownerName,
        cityName = input.cityName,
        quota = input.quota,
        name = input.name,
        beginTime = input.beginTime,
        endTime = input.endTime,
        category = input.category,
        isFavorite = input.isFavorite
    )
}