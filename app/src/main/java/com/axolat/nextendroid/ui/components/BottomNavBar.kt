package com.axolat.nextendroid.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.axolat.nextendroid.ui.theme.AppLanguage
import com.axolat.nextendroid.ui.theme.NextendoDarkBackground
import com.axolat.nextendroid.ui.theme.NextendoPink
import com.axolat.nextendroid.ui.theme.NextendoTextSecondary
import com.axolat.nextendroid.ui.theme.Strings

enum class NavTab(val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    HOME(Icons.Filled.Home, Icons.Outlined.Home),
    FRIENDS(Icons.Filled.People, Icons.Outlined.People),
    SAVES(Icons.Filled.Inventory2, Icons.Outlined.Inventory2),
    ACCOUNT(Icons.Filled.Person, Icons.Outlined.Person);

    fun title(lang: AppLanguage): String = when (this) {
        HOME -> Strings.tabHome(lang)
        FRIENDS -> Strings.tabFriends(lang)
        SAVES -> Strings.tabSaves(lang)
        ACCOUNT -> Strings.tabAccount(lang)
    }
}

@Composable
fun BottomNavBar(
    selectedTab: NavTab,
    appLanguage: AppLanguage,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(NextendoDarkBackground)
            .padding(top = 4.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(NextendoDarkBackground, RoundedCornerShape(24.dp)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) NextendoPink else NextendoTextSecondary,
                    animationSpec = tween(220),
                    label = "navTabColor"
                )
                val iconSize by animateDpAsState(
                    targetValue = if (isSelected) 26.dp else 24.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "navTabIconSize"
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onTabSelected(tab) }
                ) {
                    Icon(
                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = tab.title(appLanguage),
                        tint = contentColor,
                        modifier = Modifier.size(iconSize)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tab.title(appLanguage),
                        color = contentColor,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
