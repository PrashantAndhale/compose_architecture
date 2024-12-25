package com.example.bottomnavigationandbottomsheet.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.Calender
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.Completed
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.ResumeInstallation
import com.example.bottomnavigationandbottomsheet.navigation.bottomnavscreen.SyncInstallation
import com.example.bottomnavigationandbottomsheet.navigation.navdrawerscreen.Showmax
import com.example.bottomnavigationandbottomsheet.screens.profile.Profile
import com.example.bottomnavigationandbottomsheet.view.NewInstallation
import com.example.bottomnavigationandbottomsheet.view.Notification

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    isDashboardScreenVisible: (Boolean) -> Unit
) {
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
        composable(Screens.Profile.route) {
            Profile()
            isDashboardScreenVisible(true)
        }
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

    }
}