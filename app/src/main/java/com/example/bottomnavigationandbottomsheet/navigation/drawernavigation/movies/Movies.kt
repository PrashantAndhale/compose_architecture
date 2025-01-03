package com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.movies

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.navigation.commonnavigation.Screens
import com.example.bottomnavigationandbottomsheet.screens.NoInternetConnection
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.example.common.Resource
import com.example.domain.model.MoviesItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun Movies(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val list = viewModel.moviesFlow.collectAsLazyPagingItems()

    val movies by sharedViewModel.callbackData.collectAsState()
    LaunchedEffect(movies) {
        Log.d("Callback Data", "Profile: $movies")
    }
    val isConnected by viewModel.isConnected.collectAsState()

    val moviesState by viewModel.movies.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(top = 60.dp)
            .background(
                color = colorResource(id = R.color.bg_color)
            )
    ) {
        when (val state = moviesState) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is Resource.Success -> {
                // Show movies list
                state.data?.let {
                    LoadUI(isConnected,
                        state.data,
                        viewModel,
                        navHostController,
                        onItemClick = { item ->
                            val movieItemJson = Json.encodeToString(item)
                            val encodedMovieItemJson = Uri.encode(movieItemJson)
                            navHostController.navigate(Screens.MovieDetail.route + "/$encodedMovieItemJson")

                        })
                }
            }

            is Resource.Error -> {
                state.error?.let {
                    NoInternetConnection {}
                }
            }

            else -> {

            }
        }


    }


}

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoadUI(
    isConnected: Boolean?, list: List<MoviesItem>?,
    viewModel: MovieViewModel,
    navHostController: NavHostController,
    onItemClick: (MoviesItem) -> Unit
) {
    if (isConnected == true) {
        if (list != null) {
            MovieList(list, navHostController, onItemClick)
        }
    } else {
        NoInternetConnection {
            viewModel.clearErrorMessage()
        }
    }

}

@Composable
fun MovieList(
    movies: List<MoviesItem>,
    navHostController: NavHostController,
    onItemClick: (MoviesItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp
        ), verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(movies) { movie ->
            MovieItem(movie, navHostController, onItemClick)
        }
    }
}

@Composable
fun MovieItem(
    moviesItem: MoviesItem, navHostController: NavHostController, onItemClick: (MoviesItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 8.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.clickable { onItemClick(moviesItem) }
        ) {
            Column(
                modifier = Modifier.background(colorResource(id = R.color.card_bg_color))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    CustomText(
                        text = moviesItem.movie,
                        fontSize = 16,
                        overflow = TextOverflow.Ellipsis,
                        maxlines = 1,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(start = 12.dp, top = 8.dp)
                            .weight(3.5f)
                    )
                    CustomText(
                        text = "135+ ch",
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        maxlines = 1,
                        fontSize = 14,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.secondaryTextColor),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                CustomText(
                    text = "Best of the World, Single Installation Record, Watch, Pause with PVR",
                    textAlign = TextAlign.Center,
                    fontSize = 14,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = colorResource(id = R.color.secondaryTextColor)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, end = 8.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 16.dp,  // Top right corner
                                    bottomEnd = 16.dp  // Bottom right corner
                                )
                            )
                            .background(color = Color.White)
                            .padding(4.dp)
                    ) {
                        CustomText(
                            text = "From R 929 pm",
                            boldText = listOf("929"),
                            color = Color.Black,
                            fontSize = 14,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .width(110.dp)
                                .padding(vertical = 4.dp, horizontal = 2.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = Color.White)
                            .padding(4.dp)
                    ) {
                        CustomText(
                            text = "View Details",
                            color = Color.Black,
                            fontSize = 14,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.clickable {
                                // Handle click action
                            }
                        )
                    }
                }
            }
        }
    }
}


/*@Composable
fun MovieItem(
    moviesItem: MoviesItem, navHostController: NavHostController, onItemClick: (MoviesItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(20.dp),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.card_bg_color))
                    .clickable {
                        onItemClick(moviesItem)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, end = 12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                        ) {
                            CustomText(
                                text = "DStv Premium",
                                fontSize = 20,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Pushes the icon to the right
                        Box(
                            modifier = Modifier.padding(4.dp) // Optional padding
                        ) {
                            CustomText(
                                text = "135+ ch",
                                fontSize = 16,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomText(
                        text = "Best of the World, Single Installation " + "Record, Watch, Pause with PVR",
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.secondaryTextColor)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 16.dp,       // Top right corner
                                        bottomEnd = 16.dp   // Bottom right corner
                                    )
                                ) // Apply rounded corners
                                .background(color = Color.White) // Optional background color
                                .padding(4.dp) // Optional padding
                        ) {
                            CustomText(
                                text = "From R 929 pm",
                                boldText = listOf("929"),
                                color = Color.Black,
                                fontSize = 15,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .width(120.dp)
                                    .padding(top = 4.dp, bottom = 4.dp, start = 2.dp, end = 2.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        16.dp   // Bottom right corner
                                    )
                                ) // Apply rounded corners
                                .background(color = Color.White) // Optional background color
                                .padding(4.dp) // Optional padding
                        ) {
                            CustomText(text = "View Details",
                                color = Color.Black,
                                fontSize = 14,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier

                                    .clickable {
                                        Log.d("TAG", "MovieItem: ")
                                    })
                        }
                    }


                    *//*   Row {
                           //CircularImage()
                           Spacer(modifier = Modifier.width(8.dp))
                           CustomText(
                               text = moviesItem.movie
                           )
                       }*//*

                }


            }*//*  Card(
                  modifier = Modifier
                      .fillMaxWidth(),
                  shape = RoundedCornerShape(20.dp)
              ) {


                  *//**//*Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(0.dp),
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://webneel.com/sites/default/files/imagecache/660Thumbnail/images/blog/movie-poster-design.jpg")
                            .crossfade(true)
                            .listener(
                                onError = { _, throwable ->
                                    Log.e(
                                        "Image Load Error",
                                        throwable.throwable.message ?: "Unknown error"
                                    )
                                }
                            )
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )*//**//*
            }*//*
        }

    }
}*/

@Composable
fun CircularImage() {
    Card(

        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
            .safeContentPadding()
            .padding(8.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://upload.wikimedia.org/wikipedia/commons/7/78/Wikipedia_Profile_picture.jpg")
                    .crossfade(true)
                    .listener(
                        onError = { _, throwable ->
                            Log.e(
                                "Image Load Error",
                                throwable.throwable.message ?: "Unknown error"
                            )
                        }
                    )
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
