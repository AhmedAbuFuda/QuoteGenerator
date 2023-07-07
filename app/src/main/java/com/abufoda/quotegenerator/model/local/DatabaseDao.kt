package com.abufoda.quotegenerator.model.local

import androidx.room.*
import com.abufoda.quotegenerator.model.entity.MyDataItem
@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote:MyDataItem)

    @Delete
    suspend fun deleteQuote(quote: MyDataItem)

    @Query("select * from quote")
    suspend fun getAllQuote():List<MyDataItem>

    @Query("select COUNT(id) from quote")
    suspend fun getCount(): Int
}