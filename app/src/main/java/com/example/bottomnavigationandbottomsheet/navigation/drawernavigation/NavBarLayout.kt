package com.example.bottomnavigationandbottomsheet.navigation.drawernavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bottomnavigationandbottomsheet.R
import com.example.bottomnavigationandbottomsheet.customcontrol.CustomText
import com.example.bottomnavigationandbottomsheet.navigation.drawernavigation.models.NavigationItem

@Composable
fun NavBarHeader() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.card_bg_color))
            .padding(top = 16.dp, bottom = 24.dp),

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
                    fontSize = 20
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomText(
                    color = colorResource(id = R.color.secondaryTextColor),
                    text = "Mobile Developer",
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.bg_color))
    ) {
        items.forEachIndexed { index, navigationItem ->
            NavigationDrawerItem(
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = colorResource(id = R.color.bg_color),
                    selectedContainerColor = colorResource(id = R.color.card_bg_color)
                ),
                modifier = Modifier.padding(top = 8.dp),
                label = {
                    Text(
                        text = navigationItem.title,
                        color = colorResource(id = R.color.primaryTextColor)
                    )
            },
                selected = currentRoute == navigationItem.route,
                onClick = { onClick(navigationItem) },
                icon = {
                    Icon(
                        tint = colorResource(id = R.color.primaryTextColor),
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