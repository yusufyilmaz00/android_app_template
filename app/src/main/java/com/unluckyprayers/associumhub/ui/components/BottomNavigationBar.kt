package com.unluckyprayers.associumhub.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.unluckyprayers.associumhub.ui.navigation.Routes

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    alwaysShowLabel: Boolean = true
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    fun isSelected(route: String): Boolean {
        val dest = currentDestination ?: return false
        return dest.route == route || dest.hierarchy.any { it.route?.startsWith(route) == true }
    }

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        // PAGE 1
        NavigationBarItem(
            selected = isSelected(Routes.PAGE1),
            onClick = {
                navController.navigate(Routes.PAGE1) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                }
            },
            icon = {
                Icon(
                    imageVector = if (isSelected(Routes.PAGE1)) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "PAGE 1"
                )
            },
            label = { Text("PAGE 1", style = MaterialTheme.typography.labelMedium) },
            alwaysShowLabel = alwaysShowLabel,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        // SEARCH
        NavigationBarItem(
            selected = isSelected(Routes.PAGE2),
            onClick = {
                navController.navigate(Routes.PAGE2) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                }
            },
            icon = {
                Icon(
                    imageVector = if (isSelected(Routes.PAGE2)) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "PAGE 2"
                )
            },
            label = { Text("PAGE 2", style = MaterialTheme.typography.labelMedium) },
            alwaysShowLabel = alwaysShowLabel,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        NavigationBarItem(
            selected = isSelected(Routes.PAGE3),
            onClick = {
                navController.navigate(Routes.PAGE3) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                }
            },
            icon = {
                Icon(
                    imageVector = if (isSelected(Routes.PAGE3)) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "PAGE 3"
                )
            },
            label = { Text("PAGE 3", style = MaterialTheme.typography.labelMedium) },
            alwaysShowLabel = alwaysShowLabel,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

    }
}