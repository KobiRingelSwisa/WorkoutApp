package com.mykotlinapps.bodybuilder.data.repository

import android.app.Application
import com.mykotlinapps.bodybuilder.data.Item
import com.mykotlinapps.bodybuilder.data.local_db.ItemDao
import com.mykotlinapps.bodybuilder.data.local_db.ItemDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemRepository(application: Application)  { //:CoroutineScope {

//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.IO

    private var itemDao: ItemDao?

    init {
        val db = ItemDataBase.getDatabase(application.applicationContext)
        itemDao = db?.itemsDao()
    }

    fun getItems() = itemDao?.getItems()

    suspend fun addItem(item: Item) {
        //launch {
        itemDao?.addItem(item)
        //}
    }

    suspend fun deleteItem(item: Item) {
        //launch {
        itemDao?.deleteItem(item)
        //}
    }

    fun getItem(id: Int) = itemDao?.getItem(id)

    suspend fun deleteAll() {
        //launch {
        itemDao?.deleteAll()
        //}
    }
}