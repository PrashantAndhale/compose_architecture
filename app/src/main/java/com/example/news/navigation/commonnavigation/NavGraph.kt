package com.example.news.navigation.commonnavigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.model.Newspapers
import com.example.news.screens.news.News
import com.example.domain.model.NewsDetails
import com.example.news.screens.news.NewsDetails
import com.example.news.sharepreference.SharedPreferencesHelper
import com.example.news.shareviewmodel.SharedViewModel
import kotlinx.serialization.json.Json

@Composable
fun SetUpNavGraph(
    preferencesHelper: SharedPreferencesHelper,
    isLogin: Boolean = false,
    navController: NavHostController,
    isDashboardScreenVisible: (Boolean) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val timer = 1000
    NavHost(
        navController = navController,
        startDestination = Screens.News.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(timer)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(timer)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(timer)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(timer)
            )
        }
    )
    {

        composable(Screens.News.route) {
            News(navController, sharedViewModel)
        }
        composable(
            route = Screens.NewsDetail.route + "/{movieItem}",
            arguments = listOf(navArgument("movieItem") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieItemJson = backStackEntry.arguments?.getString("movieItem")
            val movieItem = Json.decodeFromString<Newspapers>(movieItemJson ?: "")
            isDashboardScreenVisible(false)
            NewsDetails(navController, movieItem, sharedViewModel)
        }


    }
}