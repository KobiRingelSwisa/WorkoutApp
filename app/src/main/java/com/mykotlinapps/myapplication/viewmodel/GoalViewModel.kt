package com.mykotlinapps.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mykotlinapps.myapplication.data.Goal
import com.mykotlinapps.myapplication.repository.GoalRepository
import kotlinx.coroutines.launch

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {

    val allGoals: LiveData<List<Goal>> = repository.allGoals

    fun insert(goal: Goal) = viewModelScope.launch {
        repository.insert(goal)
    }
}
