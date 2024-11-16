package com.example.androiddemo.network

import com.example.androiddemo.model.Figures
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Service {
    @GET("historicalfigures")
    fun getHistoricalFigures(@Query("name") name: String): Call<List<Figures>>
}