package com.trendyol.vsh.interview.project.ui.section

import com.trendyol.vsh.interview.project.core.ui.model.ErrorMessage

sealed interface ListingUiState<T> {
    val products: List<T>
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class Empty<T>(
        override val products: List<T>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : ListingUiState<T>

    data class Data<T>(
        override val products: List<T>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : ListingUiState<T>
}