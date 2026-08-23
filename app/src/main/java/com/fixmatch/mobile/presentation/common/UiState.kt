package com.fixmatch.mobile.presentation.common

import com.fixmatch.mobile.domain.util.DataError

sealed interface UiState<out T> {
    object Idle : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val error: DataError, val message: String) : UiState<Nothing>
    object Empty : UiState<Nothing>
}
