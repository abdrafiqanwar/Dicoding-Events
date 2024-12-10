package com.example.dicodingevents.core.data.source.remote.network

import com.example.dicodingevents.core.data.source.remote.response.ListEventResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("events")
    fun getList(): Call<ListEventResponse>
}