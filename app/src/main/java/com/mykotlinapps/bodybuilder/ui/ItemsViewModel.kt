package com.mykotlinapps.bodybuilder.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mykotlinapps.bodybuilder.Exercise
import com.mykotlinapps.bodybuilder.data.BodyStats
import com.mykotlinapps.bodybuilder.data.Item
import com.mykotlinapps.bodybuilder.data.local_db.ItemDataBase
import com.mykotlinapps.bodybuilder.data.repository.BodyStatsRepository
import com.mykotlinapps.bodybuilder.data.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ItemRepository(application)
    private val bodyStatsRepository: BodyStatsRepository
    private val exercises = mutableListOf<Exercise>()

    val items: LiveData<List<Item>>? = repository.getItems()
    val latestBodyStats: LiveData<BodyStats>

    private val _chosenItem = MutableLiveData<Item>()
    val chosenItem: LiveData<Item> get() = _chosenItem

    init {
        val bodyStatsDao = ItemDataBase.getDatabase(application).bodyStatsDao()
        bodyStatsRepository = BodyStatsRepository(bodyStatsDao)
        latestBodyStats = bodyStatsRepository.getLatestBodyStats()
    }

    fun setItem(item: Item) {
        _chosenItem.value = item
    }

    fun addItem(item: Item) {
        viewModelScope.launch {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun insertBodyStats(bodyStats: BodyStats) {
        viewModelScope.launch {
            bodyStatsRepository.insertBodyStats(bodyStats)
        }
    }

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
    }
}
