package com.mykotlinapps.myapplication.base

import androidx.room.PrimaryKey

abstract class BaseEntity(
    @PrimaryKey(autoGenerate = true) open val id: Int = 0,
    open val title: String,
    open val description: String,
    open val duration: Int
)
