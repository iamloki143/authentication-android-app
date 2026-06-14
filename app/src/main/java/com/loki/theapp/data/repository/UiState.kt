package com.loki.theapp.data.repository

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

val <T> UiState<T>.isLoading get() = this is UiState.Loading
val <T> UiState<T>.isError get() = this is UiState.Error