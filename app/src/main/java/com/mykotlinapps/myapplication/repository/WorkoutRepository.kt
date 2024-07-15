package com.mykotlinapps.myapplication.repository

import androidx.lifecycle.LiveData
import com.mykotlinapps.myapplication.data.Workout
import com.mykotlinapps.myapplication.data.WorkoutDao

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()
    val activeWorkouts: LiveData<List<Workout>> = workoutDao.getActiveWorkouts()

    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }
}
