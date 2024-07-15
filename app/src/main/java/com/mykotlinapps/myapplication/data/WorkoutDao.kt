package com.mykotlinapps.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout_table")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE isCompleted = 0")
    fun getActiveWorkouts(): LiveData<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout)
}
