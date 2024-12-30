package com.example.bottomnavigationandbottomsheet.screens.login

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomAnimatedBorderButton
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomOutlinedTextField
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.sharepreference.SharedPreferencesHelper
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel


@Composable
fun Login(
    navController: NavHostController,
    viewModel: SharedViewModel = hiltViewModel(),
    preferencesHelper: SharedPreferencesHelper,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.bg_color_screen)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillBounds // Ensure the image covers the screen and is cropped if necessary
        )
        var uname by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val state = rememberTextFieldState("")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.multichoice_logo),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop // Ensure the image covers the screen and is cropped if necessary
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(
                text = "Field Sales & Services",
                fontSize = 28,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = uname,
                onValueChange = state,
                label = "Username",
                leadingIcon = Icons.Default.Person, // Optional
                leadingIconClick = { /* Handle icon click */ } // Optional
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = password,
                onValueChange = state,
                label = "Password",
                leadingIcon = Icons.Default.Lock, // Optional
                leadingIconClick = { /* Handle icon click */ }, // Optional
                trailingVisibleIcon = R.drawable.outline_visibility_24,
                trailingInvisibleIcon = R.drawable.baseline_visibility_off_24,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomAnimatedBorderButton(onClick = {
                navController.popBackStack()
                viewModel.setLogin(true)
                preferencesHelper.isLoggedIn = true
            }, label = "Login")
        }

    }


}
