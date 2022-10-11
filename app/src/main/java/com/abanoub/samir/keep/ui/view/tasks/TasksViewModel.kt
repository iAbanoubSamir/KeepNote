package com.abanoub.samir.keep.ui.view.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abanoub.samir.keep.data.local.prefs.PreferencesRepository
import com.abanoub.samir.keep.data.local.prefs.SortOrder
import com.abanoub.samir.keep.data.local.tasks.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val preferencesFlow = preferencesRepository.preferencesFlow

    private val tasksFlow = combine(
        searchQuery,
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        taskDao.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesRepository.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesRepository.updateHideCompleted(hideCompleted)
    }

    val tasks = tasksFlow.asLiveData()
}