package com.example.dicodingevents.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListEventResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listEvents")
    val listEvents: List<EventResponse>
)
