package com.mykotlinapps.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GoalDao {

    @Insert
    suspend fun insert(goal: Goal)

    @Query("SELECT * FROM goal_table WHERE isCompleted = 0")
    fun getActiveGoals(): LiveData<List<Goal>>

    @Query("SELECT * FROM goal_table")
    fun getAllGoals(): LiveData<List<Goal>>
}
