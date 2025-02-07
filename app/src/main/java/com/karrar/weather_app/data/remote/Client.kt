package com.karrar.weather_app.data.remote

import android.util.Log
import com.google.gson.Gson
import com.karrar.weather_app.data.domain.WeatherModel
import okhttp3.*
import java.io.IOException

object Client {
    private val client = OkHttpClient()

   fun fetchData(myCallback: (result: WeatherModel?) -> Unit){

        val request = Request.Builder().url(Url.getWeatherUrl()).build()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherModel::class.java)
                    myCallback.invoke(result)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG", "onFailure: ${e.message}")
            }
        })
    }

}