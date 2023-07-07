package com.abufoda.quotegenerator.model.remote

import com.abufoda.quotegenerator.model.entity.MyDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RemoteRepositoryImp(private val api: ServiceApi): RemoteRepository {
    override suspend fun getData() =
        withContext(Dispatchers.IO){
            api.getData()
        }
}