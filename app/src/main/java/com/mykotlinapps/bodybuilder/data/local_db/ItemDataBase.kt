package com.mykotlinapps.bodybuilder.data.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mykotlinapps.bodybuilder.data.BodyStats
import com.mykotlinapps.bodybuilder.data.Item

@Database(entities = [Item::class, BodyStats::class], version =1, exportSchema = false)
abstract class ItemDataBase: RoomDatabase() {

    abstract fun itemsDao(): ItemDao
    abstract fun bodyStatsDao(): BodyStatsDao

    companion object{
        @Volatile
        private var instance: ItemDataBase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(this){
            Room.databaseBuilder(context.applicationContext, ItemDataBase::class.java,"items_db")
                .build()
        }
    }
}

