package com.example.bottomnavigationandbottomsheet.navigation.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel

@Composable
fun SyncInstallation(navController: NavHostController, sharedViewModel: SharedViewModel) {
    Box(  modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .padding(top = 60.dp)
       /* .background(
            color = colorResource(id = R.color.bg_color)
        )*/)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                text = "Sync Installation", fontSize = 24,
                fontWeight = FontWeight.Bold
            )
        }
    }
}