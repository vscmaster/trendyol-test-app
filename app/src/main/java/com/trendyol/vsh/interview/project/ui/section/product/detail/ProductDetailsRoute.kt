package com.trendyol.vsh.interview.project.ui.section.product.detail

import android.content.res.Configuration
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ProductDetailsRoute(
    productDetailsViewModel: ProductDetailsViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val listLazyListState = rememberLazyListState()
    val uiState by productDetailsViewModel.uiState.collectAsState()
    val isShowAppBar = doesNeedShowAppBar()
    ProductDetailsScreen(
        uiState = uiState,
        showTopAppBar = isShowAppBar,
        scaffoldState = scaffoldState,
        homeListLazyListState = listLazyListState,
    )
}

@Composable
fun doesNeedShowAppBar(): Boolean {
    val configuration = LocalConfiguration.current
    return when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> false
        else -> true
    }
}

