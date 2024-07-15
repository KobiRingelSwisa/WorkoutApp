package com.mykotlinapps.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    override val title: String,
    override val description: String,
    override val duration: Int,
    val name: String
) : BaseEntity(id, title, description, duration)

