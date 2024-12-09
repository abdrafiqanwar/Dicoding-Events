package com.example.dicodingevents.core.utils

import com.example.dicodingevents.core.data.source.local.entity.EventEntity
import com.example.dicodingevents.core.data.source.remote.response.EventResponse

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
}