package com.mykotlinapps.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal_table")
data class Goal(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    override val title: String,
    override val description: String,
    override val duration: Int,
    val isCompleted: Boolean
) : BaseEntity(id, title, description, duration)
