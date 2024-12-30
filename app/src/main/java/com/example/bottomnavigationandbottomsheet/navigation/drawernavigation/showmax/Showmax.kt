package com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.showmax

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomOutlinedTextField
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.customcontrol.LargeDropdownMenu
import com.example.bottomnavigationandbottomsheet.shareviewmodel.SharedViewModel

@Composable
fun Showmax(navController: NavHostController, sharedViewModel: SharedViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(top = 60.dp)
            .background(color = colorResource(id = R.color.bg_color))
    ) {
        var uname by rememberSaveable { mutableStateOf("") }
        val state = rememberTextFieldState("")

        var mExpanded by remember { mutableStateOf(false) }
        val mCities =
            listOf("Delhi", "Mumbai", "Chennai", "Kolkata", "Hyderabad", "Bengaluru", "Pune")
        var mSelectedText by remember { mutableStateOf("") }
        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

        // Up Icon when expanded and down icon when collapsed
        val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp
        else Icons.Filled.KeyboardArrowDown

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(8.dp),
        ) {
            // CustomOutlinedTextField to capture TextField size and add trailing icon
            CustomOutlinedTextField(
                value = uname,
                onValueChange = state,
                label = "Username",
                onGloballyPositioned = { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            var selectedIndex by remember { mutableStateOf(-1) }
            LargeDropdownMenu(
                label = "Installation Type",
                items = listOf("Prepaid", "Postpaid"),
                selectedIndex = selectedIndex,
                onItemSelected = { index,
                                   _ ->
                    selectedIndex = index
                },
            )
            Spacer(modifier = Modifier.height(8.dp))


            var expanded by remember { mutableStateOf(false) }
            val suggestions = listOf("Item1", "Item2", "Item3")
            var selectedText by remember { mutableStateOf("DropDown") }
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.5.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bg_color))
            )
            {
                Text(
                    text = selectedText,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f)) // Pushes the icon to the right
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            DropdownMenu(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(color = colorResource(id = R.color.primaryTextColor)),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                suggestions.forEachIndexed { index, label ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { CustomText(text = label, color = Color.Black) },
                        onClick = {
                            selectedText = label
                            expanded = false
                        }
                    )
                    if (index < suggestions.lastIndex) {
                        Divider()
                    }
                }
            }




        }
    }
}
