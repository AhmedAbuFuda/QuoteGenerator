package com.abufoda.quotegenerator.model.local

import androidx.room.Delete
import androidx.room.Query
import com.abufoda.quotegenerator.model.entity.MyDataItem

interface LocalRepository {
     suspend fun insertQuote(quote: MyDataItem)

     suspend fun deleteQuote(quote: MyDataItem)

     suspend fun getAllQuote():List<MyDataItem>

     suspend fun getCount(): Int
}