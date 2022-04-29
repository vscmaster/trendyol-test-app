package com.trendyol.vsh.interview.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.trendyol.vsh.interview.project.theme.TrendyolTheme
import com.trendyol.vsh.interview.project.ui.section.product.listing.ProductListingViewModel
import com.trendyol.vsh.interview.project.ui.section.product.slider.ProductSliderViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun productListingViewModelFactory(): ProductListingViewModel.ProductListingViewModelAssistedFactory
        fun productSliderViewModelFactory(): ProductSliderViewModel.ProductSliderViewModelAssistedFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendyolTheme {
                val navController: NavHostController = rememberAnimatedNavController()

                CompositionLocalProvider(LocalNavController provides navController) {
                    AppNavGraph()
                }
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No LocalNavController provided")
}