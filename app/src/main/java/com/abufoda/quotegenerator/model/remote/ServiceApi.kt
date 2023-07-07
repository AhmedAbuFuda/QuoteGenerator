package com.abufoda.quotegenerator.model.remote

import com.abufoda.quotegenerator.model.entity.MyDataItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    @GET("quotes/random")
    suspend fun getData(): Response<List<MyDataItem>>
}