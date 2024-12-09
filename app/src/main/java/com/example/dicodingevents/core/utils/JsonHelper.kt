package com.example.dicodingevents.core.utils

import android.content.Context
import com.example.dicodingevents.R
import com.example.dicodingevents.core.data.source.remote.response.EventResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.event).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadData(): List<EventResponse> {
        val list = ArrayList<EventResponse>()
        val responseObject = JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("listEvents")
        for (i in 0 until listArray.length()) {
            val course = listArray.getJSONObject(i)

            val summary = course.getString("summary")
            val mediaCover = course.getString("mediaCover")
            val registrants = course.getInt("registrants")
            val imageLogo = course.getString("imageLogo")
            val link = course.getString("link")
            val description = course.getString("description")
            val ownerName = course.getString("ownerName")
            val cityName = course.getString("cityName")
            val quota = course.getInt("quota")
            val name = course.getString("name")
            val id = course.getInt("id")
            val beginTime = course.getString("beginTime")
            val endTime = course.getString("endTime")
            val category = course.getString("category")

            val courseResponse = EventResponse(
                summary = summary,
                mediaCover = mediaCover,
                registrants = registrants,
                imageLogo = imageLogo,
                link = link,
                description = description,
                ownerName = ownerName,
                cityName = cityName,
                quota = quota,
                name = name,
                id = id,
                beginTime = beginTime,
                endTime = endTime,
                category = category
            )
            list.add(courseResponse)
        }
        return list
    }
}

