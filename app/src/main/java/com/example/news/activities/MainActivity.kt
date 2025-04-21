package com.example.news.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.news.navigation.commonnavigation.SetUpNavGraph
import com.example.news.screens.news.News
import com.example.news.screens.news.NewsViewModel
import com.example.news.sharepreference.SharedPreferencesHelper
import com.example.news.shareviewmodel.SharedViewModel
import com.example.news.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var helper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
        }
        setContent {
            AppTheme {
                val sharedViewModel: SharedViewModel = hiltViewModel()  // Initialize ViewModel
                val newsViewModel: NewsViewModel = hiltViewModel()  // Initialize ViewModel
                val navigationControl = rememberNavController()
                // Call your composable
                News(
                    sharedViewModel = sharedViewModel,
                    newsViewModel = newsViewModel,
                    navController = navigationControl
                )

                SetUpNavGraph(
                    helper,
                    navController = navigationControl,
                    isLogin = true,
                    sharedViewModel = sharedViewModel, isDashboardScreenVisible = {
                    }
                )

            }
        }
    }


}
