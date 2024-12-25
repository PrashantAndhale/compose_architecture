package com.example.bottomnavigationandbottomsheet.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.screens.customcontrol.CustomText

@Composable
fun NavBarHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            // .background(GreenJC)
            .padding(top = 8.dp, bottom = 8.dp),

        ) {
        Row(modifier = Modifier.padding(top = 20.dp)) {

            Image(
                painterResource(R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(top = 4.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                CustomText(
                    text = "Prashant Andhale",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22
                )
                CustomText(
                    text = "Prashant Andhale",
                    fontSize = 18
                )

            }
        }
    }
}


@Composable
fun NavBarBody(
    items: List<NavigationItem>, currentRoute: String?, onClick: (NavigationItem) -> Unit
) {
    Column {
        items.forEachIndexed { index, navigationItem ->
            NavigationDrawerItem(label = {
                Text(text = navigationItem.title)
            },
                selected = currentRoute == navigationItem.route,
                onClick = { onClick(navigationItem) },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == navigationItem.route) {
                            navigationItem.selectedIcon
                        } else {
                            navigationItem.unSelectedIcon
                        }, contentDescription = navigationItem.title
                    )
                })
        }
    }
}