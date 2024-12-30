package com.example.bottomnavigationandbottomsheet.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.navigation.commonnavigation.Screens
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomAnimatedBorderButton
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomOutlinedTextField
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel

@Composable
fun Notification(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val scrollState = rememberScrollState()
    val state = rememberTextFieldState("")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(top = 60.dp)
            .background(
                color = colorResource(id = R.color.bg_color)
            )
    ) {
        var uname by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomOutlinedTextField(
                value = uname,
                onValueChange = state,
                label = "Username",
                leadingIconClick = { /* Handle icon click */ } // Optional
            )
            CustomOutlinedTextField(
                value = uname,
                onValueChange = state,
                label = "Username",
                leadingIconClick = { /* Handle icon click */ } // Optional
            )
            CustomAnimatedBorderButton(onClick = {
                navHostController.navigate(Screens.NewInstallation.route)
            }, label = "LOGIN")
        }
    }
}
