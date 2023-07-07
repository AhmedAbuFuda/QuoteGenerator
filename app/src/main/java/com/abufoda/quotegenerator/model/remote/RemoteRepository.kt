package com.abufoda.quotegenerator.model.remote

import com.abufoda.quotegenerator.model.entity.MyDataItem
import retrofit2.Response

interface RemoteRepository {
    suspend fun getData(): Response<List<MyDataItem>>
}