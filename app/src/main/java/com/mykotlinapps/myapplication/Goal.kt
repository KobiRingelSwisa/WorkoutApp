package com.mykotlinapps.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal_table")
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)
