package com.mykotlinapps.bodybuilder.data.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mykotlinapps.bodybuilder.data.Item
import androidx.room.*

@Dao
interface ItemDao {


 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun addItem(item: Item): Long

 @Delete
 suspend fun deleteItem(item: Item): Int

 @Update
 suspend fun updateItem(item: Item): Int

 @Query("SELECT * FROM items ORDER BY name ASC")
 fun getItems(): LiveData<List<Item>>

 @Query("SELECT * FROM items WHERE id = :id")
 fun getItem(id: Int): LiveData<Item>

 @Query("DELETE FROM items")
 suspend fun deleteAll(): Int
}
