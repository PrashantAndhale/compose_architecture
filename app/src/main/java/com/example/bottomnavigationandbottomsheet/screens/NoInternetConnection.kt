package com.example.bottomnavigationandbottomsheet.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText

@Composable
fun NoInternetConnection(
    title: String = "No Internet Connection",
    description: String = "Please check your internet settings and try again.",
    onRetry: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()/*, color = colorResource(id = R.color.bg_color)*/
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomText(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 22,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(
                text = description
            )
            Spacer(modifier = Modifier.height(32.dp))

        }
    }
}