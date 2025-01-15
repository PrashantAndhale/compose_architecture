package com.example.bottomnavigationandbottomsheet.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.navigation.commonnavigation.Screens
import com.example.bottomnavigationandbottomsheet.navigation.commonnavigation.SetUpNavGraph
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.NavBarBody
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.NavBarHeader
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.models.NavigationItem
import com.example.bottomnavigationandbottomsheet.sharepreference.SharedPreferencesHelper
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.bottomnavigationandbottomsheet.ui.theme.AppTheme
import com.example.bottomnavigationandbottomsheet.utils.HandledPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var helper: SharedPreferencesHelper

    private val _isLoginCallback =
        MutableStateFlow<Boolean?>(false) // Store the data in a StateFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
        }
        setContent {
            AppTheme {
                val sharedViewModel: SharedViewModel = hiltViewModel()  // Initialize ViewModel here
               // HandledPermissions(sharedViewModel)
                val navigationControl = rememberNavController()
                val isLogin by sharedViewModel.isLogin.collectAsState()
                LaunchedEffect(isLogin) {
                    _isLoginCallback.value = isLogin
                }
                ManageInitialNavigation(sharedViewModel, navigationControl)
            }
        }
    }


    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    private fun ManageInitialNavigation(
        sharedViewModel: SharedViewModel,
        navigationControl: NavHostController
    ) {
        manageBackPress(sharedViewModel)
        if (!helper.isLoggedIn) {
            if (_isLoginCallback.value == true) {
                NavBotSheet(sharedViewModel, navigationControl, helper)
            } else
                LoadLoginScreen(
                    sharedViewModel,
                    navigationControl
                )
        } else {
            NavBotSheet(sharedViewModel, navigationControl, helper)
        }
    }

    @Composable
    private fun LoadLoginScreen(
        sharedViewModel: SharedViewModel,
        navigationControl: NavHostController
    ) {
        SetUpNavGraph(
            helper,
            navController = navigationControl,
            isLogin = true,
            sharedViewModel = sharedViewModel, isDashboardScreenVisible = {
            }
        )
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavBotSheet(
        sharedViewModel: SharedViewModel,
        navigationControl: NavHostController,
        helper: SharedPreferencesHelper
    ) {
        val coroutineScope = rememberCoroutineScope()
        val selected = remember { mutableIntStateOf(R.drawable.resume) }
        val mNavigate = remember { mutableStateOf(false) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val navBackStackEntry by navigationControl.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route?.split("/")?.get(0)
        val showBars = remember { mutableStateOf(true) }
        val topBarTitle = currentRoute ?: "Movies"

        ModalNavigationDrawer(
            modifier = Modifier.fillMaxWidth(),
            gesturesEnabled = drawerState.isOpen,
           // scrimColor = MaterialTheme.colorScheme.surfaceTint,
            drawerContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f) // Set the drawer width
                ) {
                    ModalDrawerSheet(
                        modifier = Modifier
                            .testTag("Navigation Drawer")
                        //.background(color = colorResource(id = R.color.bg_color)) // Custom background color
                    ) {
                        NavBarHeader()
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                            // .background(colorResource(id = R.color.bg_color))
                        )
                        NavBarBody(
                            items = listOf(
                                NavigationItem(
                                    title = "Movies",
                                    route = Screens.Movies.route,
                                    selectedIcon = Icons.Filled.Movie,
                                    unSelectedIcon = Icons.Outlined.Movie,
                                    badgeCount = 0
                                ),
                                NavigationItem(
                                    title = "Showmax-ATB",
                                    route = Screens.Showmax.route,
                                    selectedIcon = Icons.Filled.Favorite,
                                    unSelectedIcon = Icons.Outlined.Favorite,
                                    badgeCount = 0
                                )
                            ),
                            currentRoute = currentRoute,
                        ) { currentNavigationItem ->
                            navigationControl.navigate(currentNavigationItem.route) {
                                navigationControl.graph.startDestinationRoute?.let { startDestinationRoute ->
                                    popUpTo(startDestinationRoute) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.height(60.dp),
                        /* colors = TopAppBarDefaults.topAppBarColors(
                             containerColor = Color.Black,
                             titleContentColor = Color.White,
                             navigationIconContentColor = Color.White,
                             actionIconContentColor = Color.White
                         ),*/

                        title = {
                            CustomText(
                                text = topBarTitle,
                                fontSize = 18,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        navigationIcon = {
                            if (showBars.value) {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                                }
                            } else {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        if (navigationControl.previousBackStackEntry != null) {
                                            navigationControl.popBackStack()
                                        }
                                    }
                                }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        },
                        actions = {
                            if (showBars.value) {
                                IconButton(onClick = {
                                    navigationControl.navigate(Screens.Notification.route) {
                                        mNavigate.value = true
                                    }
                                }, modifier = Modifier
                                    .size(80.dp)
                                    .testTag("Notifications")
                                ) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                /*   contentColor = Color.White,
                                                   containerColor = Color.Red,*/
                                                modifier = Modifier
                                                    .offset(x = 4.dp, y = 0.dp)
                                            ) {
                                                CustomText(
                                                    text = "3",
                                                    //color = Color.White,
                                                    fontSize = 12,
                                                )
                                            }
                                        }) {
                                        Icon(
                                            imageVector = Icons.Default.Notifications,
                                            contentDescription = "Notifications",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .offset(x = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                },
                bottomBar = {
                    if (showBars.value) {
                        CustomBottomBar(selected, navigationControl)
                    }
                },
                floatingActionButtonPosition = FabPosition.Center,
                floatingActionButton = {
                    if (showBars.value) {
                        FloatingActionButton(
                            onClick = {
                                navigationControl.navigate(Screens.NewInstallation.route)
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .size(48.dp)
                                .offset(y = 40.dp)
                                .testTag("AddButton"),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            ) {
                SetUpNavGraph(
                    helper,
                    navController = navigationControl,
                    isLogin = true,
                    sharedViewModel = sharedViewModel,
                    isDashboardScreenVisible = { isVisibleNavBar ->
                    showBars.value = isVisibleNavBar
                    })
            }
        }
    }
    @Composable
    fun CustomBottomBar(
        selected: MutableState<Int>,
        navigationControl: NavHostController,
        modifier: Modifier = Modifier,
    ) {
        BottomAppBar(
            // containerColor = Color.Black,
            modifier = modifier
                .height(60.dp)
                .testTag("BottomBar")
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
        ) {
            IconButton(
                onClick = {
                    selected.value = R.drawable.resume
                    navigationControl.navigate(Screens.Resume.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier
                    .weight(1f)
                    .testTag("ResumeButton")
            ) {
                BadgedBox(badge = {
                    Badge(
                        /* contentColor = Color.White,
                         containerColor = Color.Red,*/
                        modifier = Modifier
                            .offset(x = 4.dp, y = (0).dp)
                    ) {
                        CustomText(
                            text = "35",
                            fontSize = 12,
                        )

                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.resume),
                        contentDescription = "Resume",
                        modifier = Modifier
                            .size(24.dp)
                            .testTag("Resume"),
                        tint = if (selected.value == R.drawable.resume) getIconTintColor(true) else getIconTintColor(
                            false
                        )
                    )
                }
            }
            IconButton(
                onClick = {
                    selected.value = R.drawable.calender

                    navigationControl.navigate(Screens.Calender.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier
                    .weight(1f)
                    .testTag("CalendarButton")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calender),
                    contentDescription = "Calendar",
                    modifier = Modifier.size(24.dp),
                    tint = if (selected.value == R.drawable.calender) getIconTintColor(true) else getIconTintColor(
                        false
                    )

                )
            }
            IconButton(
                onClick = {
                    selected.value = R.drawable.completed
                    navigationControl.navigate(Screens.Completed.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier
                    .weight(1f)
                    .testTag("CompletedButton")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.completed),
                    contentDescription = "Completed",
                    modifier = Modifier.size(24.dp),
                    tint = if (selected.value == R.drawable.completed) getIconTintColor(true) else getIconTintColor(
                        false
                    )

                )
            }
            IconButton(
                onClick = {
                    selected.value = R.drawable.sync
                    navigationControl.navigate(Screens.Sync.route) {
                        popUpTo(0)
                    }
                }, modifier = Modifier
                    .weight(1f)
                    .testTag("StarredButton")
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sync),
                    contentDescription = "Starred",
                    modifier = Modifier.size(24.dp),
                    tint = if (selected.value == R.drawable.sync) getIconTintColor(true) else getIconTintColor(
                        false
                    )

                )
            }

        }

    }


}
