package com.example.bottomnavigationandbottomsheet.view

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.navigation.Screens
import com.example.bottomnavigationandbottomsheet.screens.customcontrol.CustomAnimatedBorderButton
import com.example.bottomnavigationandbottomsheet.screens.customcontrol.CustomOutlinedTextField

@Composable
fun Notification(navHostController: NavHostController) {
    val scrollState = rememberScrollState()
    val state = rememberTextFieldState("")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
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
            CustomOutlinedTextField(value = uname,
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
