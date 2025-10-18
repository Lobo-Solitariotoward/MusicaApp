package com.example.musicaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicaapp.screens.DetailScreen
import com.example.musicaapp.screens.HomeScreen
import com.example.musicaapp.ui.theme.MusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME
                    ) {
                        composable(Routes.HOME) {
                            HomeScreen(navController)
                        }

                        composable(
                            route = Routes.DETAIL_WITH_ID,
                            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
                            DetailScreen(navController = navController, albumId = albumId)
                        }
                    }

                }
            }
        }
    }
}
