package com.mykotlinapps.myapplication

import androidx.lifecycle.LiveData

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()

    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
}
