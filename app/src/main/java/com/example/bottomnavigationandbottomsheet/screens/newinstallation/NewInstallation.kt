package com.example.bottomnavigationandbottomsheet.screens.newinstallation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomOutlinedTextField
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel

@Composable
fun NewInstallation(navController: NavHostController, sharedViewModel: SharedViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(top = 60.dp)
            /* .background(
                 color = colorResource(id = R.color.bg_color)
             )*/
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6CA4BD).copy(alpha = 0.6f), // Lighter blue at the top
                        Color(0xFF287EAD).copy(alpha = 0.8f), // Mid-tone blue
                        Color(0xFF022C4E).copy(alpha = 1f)   // Darker blue at the bottom
                    )
                )
            )
    ) {
        val isVisible = remember {
            mutableStateOf(false)  // Assuming resume.xml is in res/drawable
        }
        var uname by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val state = rememberTextFieldState("")

        var number by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            CustomOutlinedTextField(
                value = uname,
                onValueChange =state,
                label = "Username",
                leadingIcon = Icons.Default.Person, // Optional
                leadingIconClick = { /* Handle icon click */ } // Optional
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = password,
                onValueChange =state,
                label = "Password",
                leadingIcon = Icons.Default.Lock, // Optional
                leadingIconClick = { /* Handle icon click */ }, // Optional
                trailingVisibleIcon = R.drawable.outline_visibility_24,
                trailingInvisibleIcon = R.drawable.baseline_visibility_off_24,
                isPasswordVisible = isVisible
            )

        }
    }
}

