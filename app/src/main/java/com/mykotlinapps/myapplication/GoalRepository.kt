package com.mykotlinapps.myapplication

import androidx.lifecycle.LiveData

class GoalRepository(private val goalDao: GoalDao) {

    val allGoals: LiveData<List<Goal>> = goalDao.getAllGoals()
    val activeGoals: LiveData<List<Goal>> = goalDao.getActiveGoals()

    suspend fun insert(goal: Goal) {
        goalDao.insert(goal)
    }
}
