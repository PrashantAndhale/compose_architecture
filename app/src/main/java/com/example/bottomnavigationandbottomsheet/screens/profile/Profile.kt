package com.example.bottomnavigationandbottomsheet.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bottomnavigationandbottomsheet.screens.NoInternetConnection
import com.example.bottomnavigationandbottomsheet.screens.customcontrol.CustomText
import com.example.common.Resource
import com.example.domain.model.MoviesItem

@Composable
fun Profile(viewModel: ProfileViewModel = hiltViewModel()) {
    val list = viewModel.moviesFlow.collectAsLazyPagingItems()

    LoadUI(list, viewModel)
    val moviesState by viewModel.movies.collectAsState()

    when (val state = moviesState) {
        is Resource.Loading<*> -> {
            CircularProgressIndicator()
        }

        is Resource.Success<*> -> {
            // Show movies list
            state.data?.let {
                LoadUI(list, viewModel)
            }
        }

        is Resource.Error<*> -> {
            // Show error message
            state.error?.let { errorMessage ->
                NoInternetConnection {

                }
            }
        }

        else -> {

        }
    }


}

@Composable
fun LoadUI(list: LazyPagingItems<MoviesItem>, viewModel: ProfileViewModel) {
    val isConnected by viewModel.isConnected.collectAsState()
    Log.d("IsConnected", "IsConnected--->" + isConnected)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        if (isConnected == true) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 65.dp, bottom = 65.dp,
                    start = 4.dp, end = 4.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                if (list.itemSnapshotList.size > 0) {
                    items(list.itemSnapshotList) { movieItem ->
                        if (movieItem != null) {
                            MovieItem(movieItem)
                        }
                    }
                }
                when (list.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            Text("Error loading more items")
                        }
                    }

                    else -> {}
                }

            }
        } else {
            NoInternetConnection {
                viewModel.clearErrorMessage()
            }
        }
    }
}


@Composable
fun MovieItem(moviesItem: MoviesItem) {
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
                    .padding(18.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage()
                Spacer(modifier = Modifier.width(8.dp))
                CustomText(
                    text = moviesItem.movie
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Image(
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
                )
            }
        }

    }
}

@Composable
fun CircularImage() {
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp),
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
