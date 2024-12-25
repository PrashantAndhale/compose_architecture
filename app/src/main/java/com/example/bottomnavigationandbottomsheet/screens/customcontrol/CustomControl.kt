package com.example.bottomnavigationandbottomsheet.screens.customcontrol

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bottomnavigationandbottomsheet.R

@Composable
fun CustomText(
    text: String,
    fontSize: Int = 16, // Default font size
    fontWeight: FontWeight = FontWeight.Normal,
    fontFamily: FontFamily = customFontFamily
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize.sp,
            fontFamily = fontFamily,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: TextFieldState,
    label: String,
    leadingIcon: ImageVector? = null,
    leadingIconClick: (() -> Unit)? = null,
    trailingVisibleIcon: Int? = null,
    trailingInvisibleIcon: Int? = null,
    isPasswordVisible: MutableState<Boolean>? = null
) {

    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        // isError = true,
        contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
        state = onValueChange,
        lineLimits = TextFieldLineLimits.SingleLine,
        leadingIcon = if (leadingIcon != null) {
            {
                IconButton(onClick = { leadingIconClick?.invoke() }) {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                }
            }
        } else null,
        // visualTransformation = if (isPasswordVisible != null && !isPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
        label = { CustomText(label) },
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
        } else null,
    )
}

val customFontFamily = FontFamily(
    Font(R.font.avenir_book_normal, FontWeight.Normal),
    Font(R.font.avenir_heavy, FontWeight.Bold)
)

