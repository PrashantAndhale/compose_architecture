package com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomAnimatedBorderButton
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.domain.model.MoviesItem


@Composable
fun ProfileDetails(
    navController: NavHostController,
    moviesItem: MoviesItem,
    viewModel: SharedViewModel = hiltViewModel(),
) {
    val painter = painterResource(id = R.drawable.bottom_image) // Your background image
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
            .padding(top = 60.dp)
          /*  .background(
                color = colorResource(id = R.color.bg_color_screen)
            )*/
    ) {
        Image(
            painter = painter,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop // Ensure the image covers the screen and is cropped if necessary
        )
        // Ensure Column fills the height and width properly
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Optional padding for better spacing
        ) {
            CustomAnimatedBorderButton(onClick = {
                navController.popBackStack()
                viewModel.setCallbackData(moviesItem)
            }, label = "Send Data to Profile Screen", modifier = Modifier)
        }

        Image(
            painter = painter,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop // Ensure the image covers the screen and is cropped if necessary
        )
    }

}
