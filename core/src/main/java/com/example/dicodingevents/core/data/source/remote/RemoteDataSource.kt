package com.example.dicodingevents.core.data.source.remote

import android.util.Log
import com.example.dicodingevents.core.data.source.remote.network.ApiResponse
import com.example.dicodingevents.core.data.source.remote.network.ApiService
import com.example.dicodingevents.core.data.source.remote.response.EventResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllEvent(): Flow<ApiResponse<List<EventResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.listEvents
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.listEvents))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

