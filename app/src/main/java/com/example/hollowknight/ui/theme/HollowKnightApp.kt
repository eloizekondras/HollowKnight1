package com.example.hollowknight.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hollowknight.data.Character
import com.example.hollowknight.ui.theme.views.CharacterDetailScreen
import com.example.hollowknight.ui.theme.views.CharacterScreen
import com.example.hollowknight.ui.theme.views.CharacterViewModel
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HollowKnightApp() {
    val navController = rememberNavController()
    val viewModel: CharacterViewModel = viewModel()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.CharacterList.name
        ) {
            composable(AppScreens.CharacterList.name) {
                CharacterScreen(characterViewModel = viewModel,
                    onCharacterClick = { character ->

                        val characterJson = URLEncoder.encode(Gson().toJson(character), StandardCharsets.UTF_8.toString())
                        navController.navigate("${AppScreens.CharacterDetail.name}/$characterJson")
                    }
                )
            }
            composable(
                route = "${AppScreens.CharacterDetail.name}/{character}",
                arguments = listOf(navArgument("character") { type = NavType.StringType })
            ) { backStackEntry ->
                val characterJson = backStackEntry.arguments?.getString("character")
                val character = characterJson?.let {
                    Gson().fromJson(URLDecoder.decode(it, StandardCharsets.UTF_8.toString()), Character::class.java)
                }
                if (character != null) {
                    CharacterDetailScreen(character = character,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}