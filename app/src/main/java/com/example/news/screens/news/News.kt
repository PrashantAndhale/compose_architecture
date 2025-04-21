package com.example.news.screens.news

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.news.customcontrol.CustomText
import com.example.news.navigation.commonnavigation.Screens
import com.example.news.screens.NoInternetConnection
import com.example.news.shareviewmodel.SharedViewModel
import com.example.common.Resource
import com.example.domain.model.Newspapers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun News(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    newsViewModel: NewsViewModel = hiltViewModel(),
) {
    val isConnected by newsViewModel.isConnected.collectAsStateWithLifecycle()
    val moviesState by newsViewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CustomText(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = "Newspapers",
                        fontSize = 22, color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3) // Example: Blue background
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = moviesState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is Resource.Success -> {
                    state.data?.let {
                        LoadUI(
                            isConnected,
                            it,
                            newsViewModel,
                            navController
                        ) { item ->
                            val movieItemJson = Json.encodeToString(item)
                            val encodedMovieItemJson = Uri.encode(movieItemJson)
                            navController.navigate(Screens.NewsDetail.route + "/$encodedMovieItemJson")
                        }
                    }
                }

                is Resource.Error -> {
                    state.error?.let {
                        NoInternetConnection(title = it) {}
                    }
                }

                else -> {}
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoadUI(
    isConnected: Boolean?, list: List<Newspapers>?,
    viewModel: NewsViewModel,
    navHostController: NavHostController,
    onItemClick: (Newspapers) -> Unit
) {
    if (isConnected == true && list != null) {
        MovieList(list, navHostController, onItemClick)
    } else {
        NoInternetConnection {
            viewModel.clearErrorMessage()
        }
    }
}

@Composable
fun MovieList(
    movies: List<Newspapers>,
    navHostController: NavHostController,
    onItemClick: (Newspapers) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            NewspaperCard(movie, navHostController, onItemClick)
        }
    }
}

@Composable
fun NewspaperCard(
    newsItem: Newspapers,
    navHostController: NavHostController,
    onItemClick: (Newspapers) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(newsItem) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            newsItem.title?.let {
                CustomText(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            CustomText(
                text = "State: ${newsItem.state ?: "Unknown"}",
                modifier = Modifier.padding(bottom = 4.dp)
            )

            CustomText(
                text = "LCCN: ${newsItem.lccn ?: "N/A"}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}