package com.example.dicodingevents.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dicodingevents.core.data.source.remote.network.ApiResponse
import com.example.dicodingevents.core.data.source.remote.network.ApiService
import com.example.dicodingevents.core.data.source.remote.response.EventResponse
import com.example.dicodingevents.core.data.source.remote.response.ListEventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllEvent(): LiveData<ApiResponse<List<EventResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<EventResponse>>>()

        //get data from remote api
        val client = apiService.getList()

        client.enqueue(object : Callback<ListEventResponse> {
            override fun onResponse(
                call: Call<ListEventResponse>, response: Response<ListEventResponse>
            ) {
                val dataArray = response.body()?.listEvents
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListEventResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}

