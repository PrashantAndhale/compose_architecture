package com.example.news.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.common.Resource
import com.example.domain.model.Issues
import com.example.domain.model.NewsDetails
import com.example.news.R
import com.example.news.customcontrol.CustomAnimatedBorderButton
import com.example.news.shareviewmodel.SharedViewModel
import com.example.domain.model.Newspapers
import com.example.news.customcontrol.CustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetails(
    navController: NavHostController,
    moviesItem: Newspapers,
    viewModel: SharedViewModel = hiltViewModel(),
) {
    val newsViewModel: NewsViewModel = hiltViewModel()
    val isConnected by newsViewModel.isConnected.collectAsStateWithLifecycle()
    val newsdetail by newsViewModel.newsdetail.collectAsState()

    LaunchedEffect(moviesItem.lccn) {
        newsViewModel.getNewsDetail(moviesItem.lccn)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CustomText(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = "News Details",
                        fontSize = 22, color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3) // Example: Blue background
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NewsDetailScreen(newsDetails = newsdetail)
        }
    }
}

@Composable
fun NewsDetailScreen(newsDetails: Resource<NewsDetails>) {
    when (newsDetails) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            newsDetails.data?.let { detail ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    item {
                        CustomText(
                            text = "News Details",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    item {
                        DetailItem(label = "Name", value = detail.name)
                        DetailItem(label = "Publisher", value = detail.publisher)
                        DetailItem(label = "LCCN", value = detail.lccn)
                        DetailItem(
                            label = "Place of Publication",
                            value = detail.placeOfPublication
                        )
                        DetailItem(label = "Start Year", value = detail.startYear)
                        DetailItem(label = "End Year", value = detail.endYear)
                        DetailItem(label = "URL", value = detail.url)
                    }
                }
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomText(text = newsDetails.error ?: "Something went wrong", color = Color.Red)
            }
        }

        else -> {} // No-op
    }
}

@Composable
fun DetailItem(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            CustomText(
                text = label,
                color = Color.Gray
            )
            CustomText(
                text = value,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Divider(modifier = Modifier.padding(top = 8.dp))
        }
    }
}

