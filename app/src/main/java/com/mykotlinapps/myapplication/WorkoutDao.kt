package com.mykotlinapps.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout_table ORDER BY name ASC")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout)

    @Query("DELETE FROM workout_table")
    suspend fun deleteAll()
}

