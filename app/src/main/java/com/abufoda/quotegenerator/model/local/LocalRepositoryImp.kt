package com.abufoda.quotegenerator.model.local

import com.abufoda.quotegenerator.model.entity.MyDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryImp(private val db: Database): LocalRepository {
    override suspend fun insertQuote(quote: MyDataItem) = withContext(Dispatchers.IO){
        db.databaseDao().insertQuote(quote)
    }

    override suspend fun deleteQuote(quote: MyDataItem) = withContext(Dispatchers.IO){
        db.databaseDao().deleteQuote(quote)
    }

    override suspend fun getAllQuote() = withContext(Dispatchers.IO){
        db.databaseDao().getAllQuote()
    }

    override suspend fun getCount() = withContext(Dispatchers.IO){
        db.databaseDao().getCount()
    }

}