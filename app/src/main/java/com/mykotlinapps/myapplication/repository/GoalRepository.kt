package com.mykotlinapps.myapplication.repository

import androidx.lifecycle.LiveData
import com.mykotlinapps.myapplication.data.Goal
import com.mykotlinapps.myapplication.data.GoalDao

class GoalRepository(private val goalDao: GoalDao) {

    val allGoals: LiveData<List<Goal>> = goalDao.getAllGoals()
    val activeGoals: LiveData<List<Goal>> = goalDao.getActiveGoals()

    suspend fun insert(goal: Goal) {
        goalDao.insert(goal)
    }
}
