package com.mykotlinapps.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mykotlinapps.myapplication.data.Workout
import com.mykotlinapps.myapplication.repository.WorkoutRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {

    val allWorkouts: LiveData<List<Workout>> = repository.allWorkouts
    val activeWorkouts: LiveData<List<Workout>> = repository.activeWorkouts

    fun insert(workout: Workout) = viewModelScope.launch {
        repository.insert(workout)
    }
}
