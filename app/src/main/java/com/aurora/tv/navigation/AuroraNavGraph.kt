package com.aurora.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aurora.tv.feature.detail.DetailScreen
import com.aurora.tv.feature.home.HomeScreen
import com.aurora.tv.feature.player.PlayerScreen

@Composable
fun AuroraNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuroraDestinations.HOME,
    ) {
        composable(AuroraDestinations.HOME) {
            HomeScreen(
                onOpenDetail = { id -> navController.navigate("${AuroraDestinations.DETAIL}/$id") },
                onPlay = { id -> navController.navigate("${AuroraDestinations.PLAYER}/$id") },
            )
        }

        composable(
            route = "${AuroraDestinations.DETAIL}/{${AuroraArgs.ITEM_ID}}",
            arguments = listOf(navArgument(AuroraArgs.ITEM_ID) { type = NavType.StringType }),
        ) {
            val itemId = requireNotNull(it.arguments?.getString(AuroraArgs.ITEM_ID))
            DetailScreen(
                itemId = itemId,
                onPlay = { id -> navController.navigate("${AuroraDestinations.PLAYER}/$id") },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = "${AuroraDestinations.PLAYER}/{${AuroraArgs.ITEM_ID}}",
            arguments = listOf(navArgument(AuroraArgs.ITEM_ID) { type = NavType.StringType }),
        ) {
            val itemId = requireNotNull(it.arguments?.getString(AuroraArgs.ITEM_ID))
            PlayerScreen(itemId = itemId, onBack = { navController.popBackStack() })
        }
    }
}

