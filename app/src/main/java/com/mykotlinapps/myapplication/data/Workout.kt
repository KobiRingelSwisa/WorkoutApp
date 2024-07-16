package com.mykotlinapps.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mykotlinapps.myapplication.base.BaseEntity

@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    override val title: String,
    override val description: String,
    override val duration: Int,
    val name: String,
    val isCompleted: Boolean = false
) : BaseEntity(id, title, description, duration)
