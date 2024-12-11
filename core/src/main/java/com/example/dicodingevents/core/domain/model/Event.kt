package com.example.dicodingevents.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event (
    val id: Int,
    val summary: String,
    val mediaCover: String,
    val registrants: Int,
    val imageLogo: String,
    val link: String,
    val description: String,
    val ownerName: String,
    var cityName: String,
    var quota: Int,
    var name: String,
    var beginTime: String,
    var endTime: String,
    var category: String,
    var isFavorite: Boolean = false
) : Parcelable