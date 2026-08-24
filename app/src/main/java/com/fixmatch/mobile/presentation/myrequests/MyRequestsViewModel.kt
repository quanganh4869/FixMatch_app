package com.fixmatch.mobile.presentation.myrequests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fixmatch.mobile.domain.model.Job
import com.fixmatch.mobile.domain.repository.JobRepository
import com.fixmatch.mobile.domain.util.NetworkResult
import com.fixmatch.mobile.presentation.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyRequestsViewModel(
    private val jobRepository: JobRepository = com.fixmatch.mobile.di.ServiceLocator.jobRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Job>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<Job>>> = _uiState.asStateFlow()

    init {
        loadRequests()
    }

    fun loadRequests() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = jobRepository.getJobs()) {
                is NetworkResult.Success -> {
                    if (result.data.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(result.data)
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.value = UiState.Error(result.error, result.message ?: "Failed to load requests")
                }
            }
        }
    }
}
