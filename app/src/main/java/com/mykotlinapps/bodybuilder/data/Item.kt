package com.mykotlinapps.bodybuilder.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "items")
data class Item(

    @ColumnInfo(name="bodyPart")
    val bodyPart:String,

    @ColumnInfo(name="equipment")
    val equipment:String,

    @ColumnInfo(name="name")
    val name:String,

    @ColumnInfo(name="target")
    val target:String,

    @ColumnInfo(name="gifUrl")
    val gifUrl:String?): Parcelable {

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
