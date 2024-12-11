package com.example.dicodingevents.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "mediaCover")
    val mediaCover: String,

    @ColumnInfo(name = "registrants")
    val registrants: Int,

    @ColumnInfo(name = "imageLogo")
    val imageLogo: String,

    @ColumnInfo(name = "link")
    val link: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "ownerName")
    val ownerName: String,

    @ColumnInfo(name = "cityName")
    var cityName: String,

    @ColumnInfo(name = "quota")
    var quota: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "beginTime")
    var beginTime: String,

    @ColumnInfo(name = "endTime")
    var endTime: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable
