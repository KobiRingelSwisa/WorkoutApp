package com.example.arcproject.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "items")
data class Item(

    @ColumnInfo(name="content")
    val title:String,

    @ColumnInfo(name="content_desc")
    val description:String,

    @ColumnInfo(name="image")
    val photo:String?): Parcelable {

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
    }


/*
object ItemManager {

    val items : MutableList<Item> = mutableListOf()

    fun add(item: Item) {
        items.add(item)
    }
    fun remove(index:Int) {
        items.removeAt(index)
    }
}*/
