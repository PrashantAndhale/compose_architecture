package com.example.bottomnavigationandbottomsheet.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.Calender
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.Completed
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.ResumeInstallation
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.SyncInstallation
import com.example.bottomnavigationandbottomsheet.navigation.navdrawerscreen.Showmax
import com.example.bottomnavigationandbottomsheet.screens.profile.Profile
import com.example.bottomnavigationandbottomsheet.screens.profile.ProfileDetails
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.bottomnavigationandbottomsheet.view.NewInstallation
import com.example.bottomnavigationandbottomsheet.view.Notification
import com.example.domain.model.MoviesItem
import kotlinx.serialization.json.Json

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    isDashboardScreenVisible: (Boolean) -> Unit
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()  // Initialize ViewModel here
    val timer = 1000
    NavHost(navController = navController, startDestination = Screens.Profile.route,
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
            Showmax()
        }
        composable(Screens.Resume.route) {
            isDashboardScreenVisible(true)
            ResumeInstallation()
        }
        composable(Screens.Completed.route) {
            isDashboardScreenVisible(true)
            Completed()
        }
        composable(Screens.Calender.route) {
            Calender()
            isDashboardScreenVisible(true)
        }

        composable(Screens.Sync.route) {
            isDashboardScreenVisible(true)
            SyncInstallation()
        }
        composable(Screens.Notification.route) {
            isDashboardScreenVisible(false)
            Notification(navController)
        }
        composable(Screens.NewInstallation.route) {
            NewInstallation()
            isDashboardScreenVisible(false)
        }
        composable(Screens.Profile.route) {
            Profile(navController, sharedViewModel)
            isDashboardScreenVisible(true)
        }
        composable(
            route = Screens.ProfileDetail.route + "/{movieItem}",
            arguments = listOf(navArgument("movieItem") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieItemJson = backStackEntry.arguments?.getString("movieItem")
            val movieItem = Json.decodeFromString<MoviesItem>(movieItemJson ?: "")
            isDashboardScreenVisible(false)
            ProfileDetails(navController, movieItem, sharedViewModel)
        }

    }
}