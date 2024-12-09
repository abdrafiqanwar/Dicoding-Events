package com.example.dicodingevents.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventResponse (
    val summary: String,
    val mediaCover: String,
    val registrants: Int,
    val imageLogo: String,
    val link: String,
    val description: String,
    val ownerName: String,
    val cityName: String,
    val quota: Int,
    val name: String,
    val id: Int,
    val beginTime: String,
    val endTime: String,
    val category: String
) : Parcelable

