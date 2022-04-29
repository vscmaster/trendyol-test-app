package com.trendyol.vsh.interview.project.ui.section

import com.trendyol.vsh.interview.project.core.ui.model.ErrorMessage

data class ListingModelState<T>(
    val products: List<T>,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {

    fun toUiState(): ListingUiState<T> =
        if (products.isEmpty()) {
            ListingUiState.Empty(
                isLoading = isLoading,
                errorMessages = errorMessages,
                products = emptyList()
            )
        } else {
            ListingUiState.Data(
                products = products,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
}