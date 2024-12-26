package com.example.bottomnavigationandbottomsheet.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.screens.customcontrol.CustomAnimatedBorderButton
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.domain.model.MoviesItem


@Composable
fun ProfileDetails(
    navController: NavHostController,
    moviesItem: MoviesItem,
    viewModel: SharedViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        // Ensure Column fills the height and width properly
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp) // Optional padding for better spacing
        ) {
            CustomAnimatedBorderButton(onClick = {
                navController.popBackStack()
                viewModel.setCallbackData(moviesItem)
            }, label = "Send Data to Profile Screen")
        }
    }
}
