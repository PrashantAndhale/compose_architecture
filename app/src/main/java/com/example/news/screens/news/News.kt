package com.example.news.screens.news

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.common.Resource
import com.example.domain.model.Newspapers
import com.example.news.customcontrol.CustomText
import com.example.news.navigation.commonnavigation.Screens
import com.example.news.screens.NoInternetConnection
import com.example.news.shareviewmodel.SharedViewModel
import com.example.news.utils.CommonTopBar
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
            CommonTopBar(
                title = "Newspapers",
                navController = navController,
                showBackButton = false
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
        modifier = Modifier.padding(4.dp),
        contentPadding = PaddingValues(
            start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp
        ),
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
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(),
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onItemClick(newsItem) }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                newsItem.title?.let {
                    CustomText(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row{
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(25.dp) // or smaller if needed
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    }
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(25.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }
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