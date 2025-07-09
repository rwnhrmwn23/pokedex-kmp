package com.pokedex.onedev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pokedex.onedev.presentation.ui.PokemonDetailScreenRoute
import com.pokedex.onedev.presentation.ui.PokemonListScreen
import com.pokedex.onedev.presentation.viewmodel.PokemonDetailViewModel
import com.pokedex.onedev.presentation.viewmodel.PokemonViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    pokemonViewModel: PokemonViewModel,
    pokemonDetailViewModel: PokemonDetailViewModel,
) {

    NavHost(navController = navController, startDestination = Screen.Pokemon) {

        composable<Screen.Pokemon> {
            PokemonListScreen(
                viewModel = pokemonViewModel,
                onClickItem = { name ->
                    navController.navigate(Screen.PokemonDetail(name))
                }
            )
        }

        composable<Screen.PokemonDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.PokemonDetail>()

            PokemonDetailScreenRoute(
                pokemonName = args.name,
                viewModel = pokemonDetailViewModel
            )
        }
    }
}
