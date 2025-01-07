package com.example.bottomnavigationandbottomsheet.screens.login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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
    // Define animation states
    val logoScale by animateFloatAsState(targetValue = 1f, animationSpec = tween(1000))
    val formOffset by animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(500, delayMillis = 250)
    )
    val fadeInLogo = remember { Animatable(0f) }
    val fadeInContent = remember { Animatable(0f) }

    // Start fading animations on composition
    LaunchedEffect(Unit) {
        fadeInLogo.animateTo(1f, animationSpec = tween(1000))
        fadeInContent.animateTo(1f, animationSpec = tween(1000, delayMillis = 500))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg_color_screen))
    ) {
        // Background Image with fade-in
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .graphicsLayer { alpha = fadeInLogo.value },
            contentScale = ContentScale.FillBounds
        )

        var uname by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val state = rememberTextFieldState("")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .offset(y = formOffset) // Animate form appearance
                .graphicsLayer { alpha = fadeInContent.value },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            // Logo with scaling animation
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer(scaleX = logoScale, scaleY = logoScale),
                painter = painterResource(id = R.drawable.multichoice_logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop
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
                leadingIcon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = password,
                onValueChange = state,
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                trailingVisibleIcon = R.drawable.outline_visibility_24,
                trailingInvisibleIcon = R.drawable.baseline_visibility_off_24,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Animated login button with scale effect
            CustomAnimatedBorderButton(onClick = {
                navController.popBackStack()
                viewModel.setLogin(true)
                preferencesHelper.isLoggedIn = true
            }, label = "Login")
        }
    }
}

