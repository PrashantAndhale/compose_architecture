package com.example.bottomnavigationandbottomsheet.screens.notification


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomAnimatedBorderButton
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomOutlinedTextField
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.navigation.commonnavigation.Screens
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Notification(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val scrollState = rememberScrollState()
    val state = rememberTextFieldState("")
    val serachstate = rememberTextFieldState("")
    val permissionsGranted by sharedViewModel.permissionsGranted.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(top = 60.dp)
           /* .background(
                color = colorResource(id = R.color.bg_color)
            )*/
    ) {
        var uname by rememberSaveable { mutableStateOf("") }
        var search by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomOutlinedTextField(
                value = uname,
                onValueChange = state,
                label = "Username",
                leadingIconClick = { /* Handle icon click */ } // Optional
            )
            SixDigitPinScreen()

            SearchTextField(value = search, onValueChange = {
                search = it
            }, onClear = {
                search = ""
            })
            CustomAnimatedBorderButton(onClick = {
                coroutineScope.launch {
                    if (!permissionsGranted) {
                        sharedViewModel.requestPermissions()
                    } else {
                        navHostController.navigate(Screens.NewInstallation.route)
                    }
                }
            }, label = "LOGIN")
        }
    }

}

@Composable
fun SearchTextField(
    value: String, onValueChange: (String) -> Unit, onClear: () -> Unit
) {
    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            CustomText(
                text = "Search...", color = colorResource(id = R.color.secondaryTextColor)
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
           // color = colorResource(id = R.color.primaryTextColor)
        ),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search Icon",
               // tint = colorResource(id = R.color.primaryTextColor)
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(
                    onClick = { onClear() }, modifier = Modifier.padding(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Icon",
                      //  tint = colorResource(id = R.color.primaryTextColor)
                    )
                }
            }
        },
       /* colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(id = R.color.primaryTextColor),
            unfocusedTextColor = colorResource(id = R.color.primaryTextColor),
            disabledTextColor = colorResource(id = R.color.secondaryTextColor),
            focusedContainerColor = colorResource(id = R.color.bg_color),
            unfocusedContainerColor = colorResource(id = R.color.bg_color),
            cursorColor = colorResource(id = R.color.blue),
            errorCursorColor = Color.Red
        ),*/
        // Adjust padding by wrapping the inner content with padding modifier
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp) // Remove outer padding
    )
}

@Composable
fun SixDigitPinScreen() {
    val pinLength = 5 // Six digits PIN
    val state = remember {
        List(pinLength) { mutableStateOf(TextFieldValue("")) }
    }
    val focusRequesters = remember { List(pinLength) { FocusRequester() } }
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until pinLength) {
                PinDigitBox(
                    value = state[i].value, onValueChange = { newValue ->
                        if (newValue.text.length <= 1) {
                            state[i].value = newValue
                            if (newValue.text.length == 1 && i < pinLength - 1) {
                                focusRequesters[i + 1].requestFocus()

                            }
                        }
                    }, focusRequester = focusRequesters[i]
                )
            }
        }
    }
}

@Composable
fun PinDigitBox(
    value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, focusRequester: FocusRequester
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            textAlign = TextAlign.Center, fontSize = 16.sp
        ),
        //contentPadding = PaddingValues(start = 4.dp, end = 4.dp),
        shape = RoundedCornerShape(18.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .size(50.dp)
            .focusRequester(focusRequester),
       /* colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(id = R.color.primaryTextColor),
            unfocusedTextColor = colorResource(id = R.color.secondaryTextColor),
            disabledTextColor = colorResource(id = R.color.secondaryTextColor),
            focusedContainerColor = colorResource(id = R.color.bg_color),
            unfocusedContainerColor = colorResource(id = R.color.bg_color),
            cursorColor = colorResource(id = R.color.blue),
            errorCursorColor = Color.Red
        )*/
    )
}



