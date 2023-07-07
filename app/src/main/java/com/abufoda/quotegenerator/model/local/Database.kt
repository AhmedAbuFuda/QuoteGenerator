package com.abufoda.quotegenerator.model.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abufoda.quotegenerator.model.entity.MyDataItem


const val DATABASE_NAME = "quote_database"
@androidx.room.Database(entities = [MyDataItem::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun databaseDao() : DatabaseDao

    companion object{
        @Volatile
        private var instance : Database? = null
        fun getInstance(context: Context) :Database{
            return instance?: synchronized(Any()){
                instance?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): Database{
            return Room.databaseBuilder(
                context.applicationContext, Database::class.java,DATABASE_NAME
            ).build()
        }
    }
}