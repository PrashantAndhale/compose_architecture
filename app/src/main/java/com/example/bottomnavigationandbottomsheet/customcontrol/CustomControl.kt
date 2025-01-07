package com.example.bottomnavigationandbottomsheet.customcontrol

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bottomnavigationandbottomsheet.R
import com.google.android.material.color.MaterialColors.ALPHA_DISABLED
import com.google.android.material.color.MaterialColors.ALPHA_FULL

@Composable
fun CustomText(
    maxlines: Int = 10,
    overflow: TextOverflow = TextOverflow.Visible,
    text: String,
    color: Color = colorResource(id = R.color.primaryTextColor),
    fontSize: Int = 16, // Default font size
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = customFontFamily,
    textAlign: TextAlign = TextAlign.Left,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    boldText: List<String> = emptyList() // List of texts to be bold
) {
    val annotatedText = buildAnnotatedString {
        append(text)
        boldText.forEach { boldPart ->
            val startIndex = text.indexOf(boldPart)
            if (startIndex >= 0) {
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold),
                    start = startIndex,
                    end = startIndex + boldPart.length
                )
            }
        }
    }

    Text(
        maxLines = maxlines,
        overflow = overflow,
        textAlign = textAlign, modifier = modifier, text = annotatedText, color = color,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontFamily = fontFamily,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun CustomOutlinedTextField(
    readonly: Boolean = false,
    value: String,
    onValueChange: TextFieldState,
    label: String,
    leadingIcon: ImageVector? = null,
    leadingIconClick: (() -> Unit)? = null,
    trailingVisibleIcon: Int? = null,
    trailingInvisibleIcon: Int? = null,
    isPasswordVisible: MutableState<Boolean>? = null,
    onGloballyPositioned: (LayoutCoordinates) -> Unit = {} // Add this parameter
) {
    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .onGloballyPositioned(onGloballyPositioned),
        // isError = true,
        contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
        state = onValueChange, readOnly = readonly,
        lineLimits = TextFieldLineLimits.SingleLine,
        leadingIcon = if (leadingIcon != null) {
            {
                IconButton(onClick = { leadingIconClick?.invoke() }) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = colorResource(id = R.color.primaryTextColor)
                    )
                }
            }
        } else null,
        //  visualTransformation = if (isPasswordVisible != null && !isPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
        label = {
            CustomText(
                text = label,
                color = colorResource(id = R.color.primaryTextColor)
            )
        },
        trailingIcon = if (isPasswordVisible != null && trailingVisibleIcon != null && trailingInvisibleIcon != null) {
            {
                if (value.isNotEmpty()) {
                    IconButton(onClick = {
                        isPasswordVisible.value = !isPasswordVisible.value
                    }) {
                        Icon(
                            painter = painterResource(id = if (isPasswordVisible.value) trailingVisibleIcon else trailingInvisibleIcon),
                            contentDescription = null
                        )
                    }
                }
            }
        } else null, textStyle = TextStyle(color = colorResource(id = R.color.primaryTextColor))
    )
}

val customFontFamily = FontFamily(
    Font(R.font.avenir_book_normal, FontWeight.Normal),
    Font(R.font.avenir_heavy, FontWeight.Bold)
)

@Composable
fun CustomAnimatedBorderButton(onClick: () -> Unit, label: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val borderColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = colorResource(id = R.color.blue),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 4.dp, color = borderColor, shape = RoundedCornerShape(20.dp)
            ),
        //  colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue))
    ) {
        CustomText(
            text = label, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun <T> LargeDropdownMenu(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    notSetLabel: String? = null,
    items: List<T>,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    selectedItemToString: (T) -> String = { it.toString() },
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit = { item, selected, itemEnabled, onClick ->
        LargeDropdownMenuItem(
            text = item.toString(),
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
        )
    },
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            shape = RoundedCornerShape(20.dp),
            label = { CustomText(text = label) },
            value = items.getOrNull(selectedIndex)?.let { selectedItemToString(it) } ?: "",
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            // contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
            trailingIcon = {
                val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colorResource(id = R.color.primaryTextColor)
                    )
                }
            },
            onValueChange = { },
            readOnly = true,
            textStyle = TextStyle(color = colorResource(id = R.color.primaryTextColor))
        )


        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .clickable(enabled = enabled) { expanded = true },
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false },
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                val listState = rememberLazyListState()
                if (selectedIndex > -1) {
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                    if (notSetLabel != null) {
                        item {
                            LargeDropdownMenuItem(
                                text = notSetLabel,
                                selected = false,
                                enabled = true,
                                onClick = { },
                            )
                        }
                    }
                    itemsIndexed(items) { index, item ->
                        val selectedItem = index == selectedIndex
                        drawItem(
                            item, selectedItem, true
                        ) {
                            onItemSelected(index, item)
                            expanded = false
                        }

                        if (index < items.lastIndex) {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun LargeDropdownMenuItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_DISABLED)
        selected -> MaterialTheme.colorScheme.primary.copy(alpha = ALPHA_FULL)
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_FULL)
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}




