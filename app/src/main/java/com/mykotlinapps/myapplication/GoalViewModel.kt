package com.mykotlinapps.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GoalViewModel : ViewModel() {

    private val _activeGoals = MutableLiveData<List<Goal>>()
    val activeGoals: LiveData<List<Goal>> get() = _activeGoals

    init {
        // Load or initialize your goals here
        _activeGoals.value = listOf(
            Goal(id = 1, title = "Goal 1", description = "Description 1", duration = 30, isCompleted = false),
            Goal(id = 2, title = "Goal 2", description = "Description 2", duration = 60, isCompleted = false)
        )
    }
}
