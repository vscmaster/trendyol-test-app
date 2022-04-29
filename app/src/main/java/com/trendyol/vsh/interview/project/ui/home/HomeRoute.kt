package com.trendyol.vsh.interview.project.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun HomeRoute(
    homeScreenViewModel: HomeScreenViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val listLazyListState = rememberLazyListState()
    val uiState by homeScreenViewModel.uiState.collectAsState()
    val isShowAppBar = doesNeedShowAppBar()
    HomeScreen(
        uiState = uiState,
        showTopAppBar = isShowAppBar,
        scaffoldState = scaffoldState,
        homeListLazyListState = listLazyListState,
        onRetry = homeScreenViewModel::refreshLayout,
        onCancelRetry = homeScreenViewModel::cancelRetry,
        onBannerCLick = {},
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

