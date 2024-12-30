package com.example.bottomnavigationandbottomsheet.navigation.commonnavigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavigation.Calender
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavigation.Completed
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavigation.ResumeInstallation
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavigation.SyncInstallation
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.movies.Movies
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.movies.ProfileDetails
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.showmax.Showmax
import com.example.bottomnavigationandbottomsheet.screens.login.Login
import com.example.bottomnavigationandbottomsheet.screens.newinstallation.NewInstallation
import com.example.bottomnavigationandbottomsheet.screens.notification.Notification
import com.example.bottomnavigationandbottomsheet.sharepreference.SharedPreferencesHelper
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.domain.model.MoviesItem
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
        startDestination = if (!preferencesHelper.isLoggedIn) Screens.Login.route else Screens.Movies.route,
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

        composable(Screens.Showmax.route) {
            isDashboardScreenVisible(true)
            Showmax(navController, sharedViewModel)
        }
        composable(Screens.Resume.route) {
            isDashboardScreenVisible(true)
            ResumeInstallation(navController, sharedViewModel)
        }
        composable(Screens.Completed.route) {
            isDashboardScreenVisible(true)
            Completed(navController, sharedViewModel)
        }
        composable(Screens.Calender.route) {
            Calender(navController, sharedViewModel)
            isDashboardScreenVisible(true)
        }

        composable(Screens.Sync.route) {
            isDashboardScreenVisible(true)
            SyncInstallation(navController, sharedViewModel)
        }
        composable(Screens.Notification.route) {
            isDashboardScreenVisible(false)
            Notification(navController, sharedViewModel)
        }
        composable(Screens.NewInstallation.route) {
            NewInstallation(navController, sharedViewModel)
            isDashboardScreenVisible(false)
        }
        composable(Screens.Movies.route) {
            Movies(navController, sharedViewModel)
            isDashboardScreenVisible(true)
        }
        composable(
            route = Screens.MovieDetail.route + "/{movieItem}",
            arguments = listOf(navArgument("movieItem") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieItemJson = backStackEntry.arguments?.getString("movieItem")
            val movieItem = Json.decodeFromString<MoviesItem>(movieItemJson ?: "")
            isDashboardScreenVisible(false)
            ProfileDetails(navController, movieItem, sharedViewModel)
        }
        composable(Screens.Login.route) {
            Login(navController, sharedViewModel, preferencesHelper)
            isDashboardScreenVisible(false)
        }

    }
}