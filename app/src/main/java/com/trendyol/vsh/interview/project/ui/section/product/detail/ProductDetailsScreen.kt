package com.trendyol.vsh.interview.project.ui.section.product.detail

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.ui.FullScreenLoading
import com.trendyol.vsh.interview.project.ui.component.AppBar
import com.trendyol.vsh.interview.project.utils.isScrolled

@Composable
fun ProductDetailsScreen(
    uiState: ProductDetailsViewModel.ProductUiState,
    showTopAppBar: Boolean,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenScrollable(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier,
    ) { detailsUiState ->
        ProductDetails(
            product = detailsUiState.product,
        )
    }
}

@Composable
private fun HomeScreenScrollable(
    uiState: ProductDetailsViewModel.ProductUiState,
    showTopAppBar: Boolean,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    detailsContent: @Composable (
        uiState: ProductDetailsViewModel.ProductUiState.Data,
    ) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (showTopAppBar) {
                AppBar(
                    elevation = if (!homeListLazyListState.isScrolled) 0.dp else 4.dp,
                    title = uiState.product?.categoryName
                        ?: stringResource(id = R.string.detail_header)
                )
            }
        },
        modifier = modifier
    ) {
        LoadingContent(
            empty = when (uiState) {
                is ProductDetailsViewModel.ProductUiState.Data -> false
            },
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            content = {
                detailsContent(uiState)
            }
        )
    }
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        content()
        if (loading) {
            FullScreenLoading()
        }
    }
}

