package com.trendyol.vsh.interview.project

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object RouteDestinations {
    const val HOME_ROUTE = "home"
    const val PRODUCT_DETAILS_ROUTE = "product_details"
}


class AppNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(RouteDestinations.HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
