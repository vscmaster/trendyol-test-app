package com.trendyol.vsh.interview.project

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.trendyol.vsh.interview.project.ui.home.HomeRoute
import com.trendyol.vsh.interview.project.ui.home.HomeScreenViewModel
import com.trendyol.vsh.interview.project.ui.section.product.detail.ProductDetailsRoute
import com.trendyol.vsh.interview.project.ui.section.product.detail.ProductDetailsViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = RouteDestinations.HOME_ROUTE
) {

    AnimatedNavHost(
        navController = LocalNavController.current,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(RouteDestinations.HOME_ROUTE) {
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            HomeRoute(
                homeScreenViewModel = homeScreenViewModel,
            )
        }
        composable(
            "${RouteDestinations.PRODUCT_DETAILS_ROUTE}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
        ) {
            val productViewModel = hiltViewModel<ProductDetailsViewModel>()

            ProductDetailsRoute(
                productDetailsViewModel = productViewModel,
            )
        }
    }
}
