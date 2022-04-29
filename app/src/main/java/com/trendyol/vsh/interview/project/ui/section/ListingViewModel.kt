package com.trendyol.vsh.interview.project.ui.section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.core.ui.model.ErrorMessage
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

abstract class ListingViewModel<T> : ViewModel() {

    private val viewModelState = MutableStateFlow(
        ListingModelState<T>(
            isLoading = true,
            products = emptyList()
        )
    )

    val uiState = viewModelState
        .map {
            it.toUiState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    abstract suspend fun repositoryMethod(args: Map<String, Any>): Result<List<T>>

    fun refreshLayout(args: Map<String, Any>) {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = repositoryMethod(args)
            viewModelState.update {
                when {
                    result.isSuccess -> it.copy(
                        products = result.getOrDefault(emptyList()),
                        isLoading = false,
                        errorMessages = emptyList()
                    )
                    else -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error_check_connection
                        )
                        it.copy(
                            errorMessages = errorMessages,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}