package com.trendyol.vsh.interview.project.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.trendyol.vsh.interview.project.R
import com.trendyol.vsh.interview.project.core.ui.model.*
import com.trendyol.vsh.interview.project.ui.FullScreenLoading
import com.trendyol.vsh.interview.project.ui.RetryMessage
import com.trendyol.vsh.interview.project.ui.component.AppBar
import com.trendyol.vsh.interview.project.ui.section.banner.BannerSection
import com.trendyol.vsh.interview.project.ui.section.banner.CarouselBannerSection
import com.trendyol.vsh.interview.project.ui.section.product.listing.ProductListingSection
import com.trendyol.vsh.interview.project.ui.section.product.slider.ProductSliderSection
import com.trendyol.vsh.interview.project.utils.isScrolled

@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    showTopAppBar: Boolean,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    onCancelRetry: () -> Unit,
    onRetry: () -> Unit,
    onBannerCLick: (String) -> Unit,
) {
    HomeScreenScrollable(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier,
        onRetry = onRetry,
        onCancelRetry = onCancelRetry,
    ) { homeUiState, contentModifier ->
        HomeContent(
            homeScreenLayout = homeUiState.homeScreenLayout,
            modifier = contentModifier,
            onBannerCLick = onBannerCLick,
        )
    }
}

@Composable
fun HomeContent(
    homeScreenLayout: HomeScreenLayout,
    modifier: Modifier,
    onBannerCLick: (String) -> Unit,
) {

    val homeLayout: HomeScreenLayout = remember { homeScreenLayout }

    val columnState = rememberLazyListState()
    LazyColumn(modifier = modifier, state = columnState) {
        itemsIndexed(homeLayout.widgets) { index, item ->
            val topPadding = Modifier.padding(
                top = if (index > 0) 12.dp else 0.dp,
                bottom = if (index == homeLayout.widgets.size - 1) 12.dp else 0.dp
            )

            when (item) {
                is SingleBanner -> {
                    BannerSection(
                        banner = item,
                        modifier = topPadding,
                        onBannerClick = onBannerCLick
                    )
                }
                is ProductListing -> {
                    ProductListingSection(
                        productListing = item,
                    )
                }
                is ProductSlider -> {
                    ProductSliderSection(
                        productSlider = item,
                        modifier = topPadding,
                    )
                }
                is CarouselBanner -> {
                    CarouselBannerSection(
                        banner = item,
                        modifier = topPadding,
                        onBannerCLick = onBannerCLick
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeScreenScrollable(
    uiState: HomeScreenUiState,
    showTopAppBar: Boolean,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onCancelRetry: () -> Unit,
    homeContent: @Composable (
        uiState: HomeScreenUiState.Data,
        modifier: Modifier
    ) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (showTopAppBar) {
                AppBar(
                    elevation = if (!homeListLazyListState.isScrolled) 0.dp else 4.dp,
                    title = stringResource(id = R.string.app_name)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        var isVisible by rememberSaveable { mutableStateOf(true) }
        if (uiState.isRetryEnabled != isVisible) {
            isVisible = uiState.isRetryEnabled
        }
        LoadingContent(
            empty = when (uiState) {
                is HomeScreenUiState.Data -> false
                is HomeScreenUiState.Empty -> uiState.isLoading
            },
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            content = {
                when (uiState) {
                    is HomeScreenUiState.Data -> {
                        homeContent(uiState, contentModifier)
                        if (uiState.errorMessages.isNotEmpty()) {
                            RetryMessage(
                                uiState.errorMessages,
                                onRetry = onRetry,
                                cancelable = true,
                                isVisible = isVisible,
                                onCancel = {
                                    isVisible = false
                                    onCancelRetry()
                                })
                        }
                    }
                    is HomeScreenUiState.Empty -> {
                        if (uiState.errorMessages.isNotEmpty()) {
                            RetryMessage(
                                uiState.errorMessages,
                                onRetry = onRetry,
                                isVisible = isVisible,
                                onCancel = {
                                    isVisible = false
                                    onCancelRetry()
                                })
                        }
                    }
                }
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

