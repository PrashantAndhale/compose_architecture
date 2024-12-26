package com.example.bottomnavigationandbottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigationandbottomsheet.navigation.NavBarBody
import com.example.bottomnavigationandbottomsheet.navigation.NavBarHeader
import com.example.bottomnavigationandbottomsheet.navigation.NavigationItem
import com.example.bottomnavigationandbottomsheet.navigation.Screens
import com.example.bottomnavigationandbottomsheet.navigation.SetUpNavGraph
import com.example.bottomnavigationandbottomsheet.screens.BaseActivity
import com.example.bottomnavigationandbottomsheet.screens.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AppTheme {
                NavBotSheet()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NavBotSheet() {
        val items = listOf(
            NavigationItem(
                title = "Profile",
                route = Screens.Profile.route,
                selectedIcon = Icons.Filled.Person,
                unSelectedIcon = Icons.Outlined.Person,
                badgeCount = 0
            ), NavigationItem(
                title = "Showmax-ATB",
                route = Screens.Showmax.route,
                selectedIcon = Icons.Filled.Favorite,
                unSelectedIcon = Icons.Outlined.Favorite,
                badgeCount = 0
            )
        )
        val navigationControl = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val selected = remember {
            mutableIntStateOf(R.drawable.resume)  // Assuming resume.xml is in res/drawable
        }
        val mNavigate = remember {
            mutableStateOf(false)  // Assuming resume.xml is in res/drawable
        }

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val navBackStackEntry by navigationControl.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val showBars = remember { mutableStateOf(true) } // State to track visibility of bars

        val topBarTitle = currentRoute ?: "Profile"
        ModalNavigationDrawer(
            gesturesEnabled = drawerState.isOpen, drawerContent = {
                ModalDrawerSheet(modifier = Modifier.testTag("Navigation Drawer")) {
                    NavBarHeader()
                    Spacer(modifier = Modifier.height(8.dp))
                    NavBarBody(
                        items = items, currentRoute = currentRoute
                    ) { currentNavigationItem ->
                        run {
                            navigationControl.navigate(currentNavigationItem.route) {
                                navigationControl.graph.startDestinationRoute?.let { startDestinationRoute ->
                                    popUpTo(startDestinationRoute) {
                                        saveState = true
                                    }
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
            }, drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        CustomText(
                            text = topBarTitle,
                            fontSize = 20,
                            fontWeight = FontWeight.Bold
                        )
                    },
                        colors = TopAppBarDefaults.topAppBarColors(
                            titleContentColor = Color.White,
                            containerColor = colorResource(id = R.color.blue)
                        ),
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

                        }, actions = {
                            if (showBars.value) {
                                IconButton(onClick = {
                                    navigationControl.navigate(Screens.Notification.route) {
                                        mNavigate.value = true
                                    }
                                }, modifier = Modifier
                                    .size(80.dp)
                                    .testTag("Notifications")
                                ) {
                                    BadgedBox(modifier = Modifier.padding(end = 10.dp), badge = {
                                        Badge {
                                            Text(text = "8")
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
                        })

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
                            onClick = { },
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
                },
            ) {
                SetUpNavGraph(navController = navigationControl) { isVisibleNavBar ->
                    showBars.value = isVisibleNavBar
                }
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
            containerColor = colorResource(id = R.color.blue),
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
                    Badge(modifier = Modifier.offset(x = 4.dp, y = (0).dp)) {
                        CustomText(
                            text = "3",
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
                        tint = if (selected.value == R.drawable.resume) Color.White else Color.DarkGray
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
                    tint = if (selected.value == R.drawable.calender) Color.White else Color.DarkGray

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
                    tint = if (selected.value == R.drawable.completed) Color.White else Color.DarkGray

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
                    tint = if (selected.value == R.drawable.sync) Color.White else Color.DarkGray

                )
            }

        }

    }
}
