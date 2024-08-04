package com.deepak.pokequest.presenter.navigation

import PokeQuestDetailsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.deepak.pokequest.presenter.feature.pokelist.PokemonListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            PokemonListScreen(navController)
        }
        composable(
            route = "${NavigationItem.Details.route}/{dominantColor}/{pokemonId}",
            arguments = listOf(
                navArgument("dominantColor") { type = NavType.IntType },
                navArgument("pokemonId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val dominantColor = backStackEntry.arguments?.getInt("dominantColor") ?: 0
            val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: ""
            PokeQuestDetailsScreen(navController, dominantColor, pokemonId)
        }

    }
}