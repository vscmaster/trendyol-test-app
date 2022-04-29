package com.trendyol.vsh.interview.project.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.core.data.TrendyolRepository
import com.trendyol.vsh.interview.project.core.ui.model.ErrorMessage
import com.trendyol.vsh.interview.project.core.ui.model.HomeScreenLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

sealed interface HomeScreenUiState {

    val homeScreenLayout: HomeScreenLayout?
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val isRetryEnabled: Boolean

    data class Empty(
        override val homeScreenLayout: HomeScreenLayout?,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val isRetryEnabled: Boolean,
    ) : HomeScreenUiState

    data class Data(
        override val homeScreenLayout: HomeScreenLayout,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val isRetryEnabled: Boolean,
    ) : HomeScreenUiState
}

private data class HomeStateModelState(
    val homeLayout: HomeScreenLayout?,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val isRetryEnabled: Boolean = true
) {

    fun toUiState(): HomeScreenUiState =
        if (homeLayout == null) {
            HomeScreenUiState.Empty(
                homeScreenLayout = null,
                isLoading = isLoading,
                errorMessages = errorMessages,
                isRetryEnabled = isRetryEnabled,
            )
        } else {
            HomeScreenUiState.Data(
                homeScreenLayout = homeLayout,
                isLoading = isLoading,
                errorMessages = errorMessages,
                isRetryEnabled = isRetryEnabled,
            )
        }
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val trendyolRepository: TrendyolRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        HomeStateModelState(
            isLoading = true,
            homeLayout = null
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

    init {
        refreshLayout()
    }

    fun refreshLayout() {
        viewModelState.update { it.copy(isLoading = true, isRetryEnabled = false) }

        viewModelScope.launch {
            val result = trendyolRepository.getHomeLayout()
            viewModelState.update {
                when {
                    result.isSuccess -> it.copy(
                        homeLayout = result.getOrNull(),
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
                            isRetryEnabled = true
                        )
                    }
                }
            }
        }
    }

    fun cancelRetry() {
        viewModelState.update { it.copy(isRetryEnabled = false) }
    }
}
