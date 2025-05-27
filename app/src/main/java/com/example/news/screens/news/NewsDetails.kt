package com.example.news.screens.news

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.common.Resource
import com.example.domain.model.NewsDetails
import com.example.domain.model.Newspapers
import com.example.news.R
import com.example.news.customcontrol.CustomText
import com.example.news.shareviewmodel.SharedViewModel
import com.example.news.utils.CommonTopBar
import kotlin.math.roundToInt

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
    Scaffold(topBar = {
        CommonTopBar(title = "News Details", navController = navController)
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
             NewsDetailScreen(newsDetails = newsdetail)
            val randomNumber = (0..9).random()
           // EmotionScaleScreen(10,randomNumber)
        }
    }
}







@Composable
fun EmotionScaleScreen(total:Int=0,selectedTick: Int = 0) {
    var currentColor by remember { mutableStateOf(Color.Gray) }
    var currentOffset by remember { mutableStateOf(0f) }

    val paddingPx = with(LocalDensity.current) { 24.dp.toPx() }
    val indicatorY = with(LocalDensity.current) {
        (paddingPx + currentOffset).toDp() - 28.dp
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .height(350.dp)
                    .width(240.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 8.dp)
                        .offset(y = indicatorY)
                ) {
                    Text(
                        text = "You're\nSettled",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = currentColor
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.selector),
                        contentDescription = "Arrow Indicator",
                        tint = currentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                EmotionScaleBar(total,
                    selectedTick = selectedTick, // ← use dynamic value
                    onOffsetChanged = { offset -> currentOffset = offset },
                    onColorChange = { color -> currentColor = color }
                )

                Text(
                    text = "Balance",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = currentColor,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                )
            }
        }
    }
}
@Composable
fun EmotionScaleBarWithTicks(
    height: Dp, tickCount: Int = 0, verticalPadding: Dp = 24.dp, currentColor: (Color) -> Unit
) {
    Canvas(
        modifier = Modifier
            .width(60.dp)
            .height(height)
    ) {
        val barWidth = size.width
        val barHeight = size.height
        val cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
        val borderColor = Color(0xADAB814D)

        // Gradient bar
        drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xADAB814D), Color(0x597DB75F)), startY = 0f, endY = barHeight
            ), size = size, cornerRadius = cornerRadius
        )

        // Border
        drawRoundRect(
            color = borderColor,
            size = size,
            cornerRadius = cornerRadius,
            style = Stroke(width = 2.dp.toPx())
        )

        // Ticks
        val paddingPx = verticalPadding.toPx()
        val effectiveHeight = barHeight - 2 * paddingPx
        val tickSpacing = effectiveHeight / (tickCount - 1)
        val longTick = 12.dp.toPx()
        val shortTick = 6.dp.toPx()

        for (i in 0 until tickCount) {
            val y = paddingPx + i * tickSpacing
            val tickLength = if (i % 2 == 0) longTick else shortTick
            drawLine(
                color = borderColor,
                start = Offset(0f, y),
                end = Offset(tickLength, y),
                strokeWidth = 2.dp.toPx()
            )
        }

        // Draw center white horizontal line
        val centerY = barHeight / 2f
        drawLine(
            color = Color(0xC6F3EBEB),
            start = Offset(0f, centerY),
            end = Offset(barWidth, centerY),
            strokeWidth = 2.dp.toPx()
        )
    }
}
@Composable
fun EmotionScaleBar(
    total: Int,
    selectedTick: Int,
    onOffsetChanged: (Float) -> Unit,
    onColorChange: (Color) -> Unit,
) {
    val verticalPadding = 30.dp
    val totalTicks = total

    BoxWithConstraints(
        modifier = Modifier
            .height(350.dp)
            .wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        val barHeight = maxHeight
        val barHeightPx = with(LocalDensity.current) { barHeight.toPx() }
        val paddingPx = with(LocalDensity.current) { verticalPadding.toPx() }
        val effectiveHeight = barHeightPx - 2 * paddingPx
        val tickSpacing = effectiveHeight / (totalTicks - 1)

        // Animate from bottom (max) to top (min)
        val dragOffset = remember { Animatable(initialValue = effectiveHeight) }

        LaunchedEffect(selectedTick) {
            val target = effectiveHeight - (selectedTick.coerceIn(0, totalTicks - 1) * tickSpacing)
            dragOffset.animateTo(
                target,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
        }

        val indicatorY = with(LocalDensity.current) {
            (paddingPx + dragOffset.value - 30.dp.toPx()).toDp()
        }

        // Draw scale
        EmotionScaleBarWithTicks(
            height = barHeight,
            tickCount = totalTicks,
            verticalPadding = verticalPadding,
        ) { color -> onColorChange(color) }

        val currentTickIndex =
            ((effectiveHeight - dragOffset.value) / tickSpacing).roundToInt().coerceIn(0, totalTicks - 1)
        val currentColor = getColorForTick(currentTickIndex)

        onColorChange(currentColor)
        onOffsetChanged(dragOffset.value)

        Box(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopCenter)
                .offset(y = indicatorY),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = currentColor, shape = CircleShape)
            )
        }
    }
}

fun getColorForTick(tick: Int): Color {
    return when (tick) {
        in 0..3 -> {
            val darkGreen = Color(0xFF388E3C)   // dark green
            val lightGreen = Color(0xFFA5D6A7)  // light green
            val fraction = (tick - 0) / 3f
            lerp(darkGreen, lightGreen, fraction) // reverse: light → dark
        }
        in 4..6 -> {
            val lightYellow = Color(0xFFFFF176) // light yellow
            val darkYellow = Color(0xFFFFC107)  // dark yellow
            val fraction = (tick - 4) / 2f
            lerp(darkYellow, lightYellow, 1f - fraction) // reverse
        }

        in 7..9 -> {
            val lightRed = Color(0xFFE57373)    // light red
            val darkRed = Color(0xFFB71C1C)      // dark red
            val fraction = (tick - 7) / 2f
            lerp(lightRed, darkRed, fraction) // reverse: light → dark
        }

        else -> Color.Gray
    }
}

/*@Composable
fun TwoToneGradientIndicator() {
    Canvas(
        modifier = Modifier
            .size(60.dp)
    ) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)

        // Top Half: Yellow Gradient (bright to deeper yellow)
        drawArc(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFF176), // Light Yellow
                    Color(0xFFFBC02D)  // Deeper Yellow
                ),
                startY = 0f,
                endY = center.y
            ),
            startAngle = -180f,
            sweepAngle = 180f,
            useCenter = true,
            size = size
        )

        // Bottom Half: Green Gradient (light to dark green)
        drawArc(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF81C784), // Light Green
                    Color(0xFF388E3C)  // Dark Green
                ),
                startY = center.y,
                endY = size.height
            ),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            size = size
        )

        // White horizontal divider line
        drawLine(
            color = Color.White,
            start = Offset(0f, center.y),
            end = Offset(size.width, center.y),
            strokeWidth = 2.dp.toPx()
        )
    }
}*/

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
                            label = "Place of Publication", value = detail.placeOfPublication
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
                text = label, color = Color.Gray
            )
            CustomText(
                text = value, fontWeight = FontWeight.SemiBold, color = Color.Black
            )
            Divider(modifier = Modifier.padding(top = 8.dp))
        }
    }
}
