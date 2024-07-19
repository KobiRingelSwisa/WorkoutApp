package com.mykotlinapps.bodybuilder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "body_stats")
data class BodyStats(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: Float,
    val bodyFat: Float,
    val waistSize: Float
)
