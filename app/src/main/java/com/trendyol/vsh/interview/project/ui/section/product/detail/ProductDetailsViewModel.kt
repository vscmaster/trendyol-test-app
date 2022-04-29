package com.trendyol.vsh.interview.project.ui.section.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trendyol.vsh.interview.project.core.data.TrendyolRepository
import com.trendyol.vsh.interview.project.core.ui.model.ErrorMessage
import com.trendyol.vsh.interview.project.core.ui.model.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val trendyolRepository: TrendyolRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    sealed interface ProductUiState {
        val product: ProductItem?
        val isLoading: Boolean
        val errorMessages: List<ErrorMessage>

        data class Data(
            override val product: ProductItem?,
            override val isLoading: Boolean,
            override val errorMessages: List<ErrorMessage>,
        ) : ProductUiState
    }

    data class ProductModelState(
        val product: ProductItem? = null,
        val isLoading: Boolean = false,
        val errorMessages: List<ErrorMessage> = emptyList(),
    ) {

        fun toUiState(): ProductUiState =
            ProductUiState.Data(
                product = product,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
    }

    private val viewModelState = MutableStateFlow(
        ProductModelState(
            isLoading = true,
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

    private fun refreshLayout() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val productId: Int = savedStateHandle["productId"] ?: -1
            val result = trendyolRepository.getProduct(productId)
            viewModelState.update {
                it.copy(
                    product = result,
                    isLoading = false,
                    errorMessages = emptyList()
                )
            }
        }
    }
}