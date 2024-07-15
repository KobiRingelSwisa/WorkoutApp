package com.mykotlinapps.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutRepository
    val allWorkouts: LiveData<List<Workout>>

    init {
        val workoutsDao = WorkoutDatabase.getDatabase(application).workoutDao()
        repository = WorkoutRepository(workoutsDao)
        allWorkouts = repository.allWorkouts
    }

    fun insert(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }
}


