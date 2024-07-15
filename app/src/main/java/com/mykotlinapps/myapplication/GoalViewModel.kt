package com.mykotlinapps.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GoalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GoalRepository
    val allGoals: LiveData<List<Goal>>
    val activeGoals: LiveData<List<Goal>>

    init {
        val goalDao = WorkoutDatabase.getDatabase(application).goalDao()
        repository = GoalRepository(goalDao)
        allGoals = repository.allGoals
        activeGoals = repository.activeGoals
    }

    fun insert(goal: Goal) = viewModelScope.launch {
        repository.insert(goal)
    }
}
