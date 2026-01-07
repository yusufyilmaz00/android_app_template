package com.unluckyprayers.associumhub.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.unluckyprayers.associumhub.ui.navigation.Routes
import com.unluckyprayers.associumhub.R

data class BottomNavItem(
    val route: String,
    val labelResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    userRole: String = "standard_user",
    alwaysShowLabel: Boolean = true
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    fun isSelected(route: String): Boolean {
        val dest = currentDestination ?: return false
        return dest.route == route || dest.hierarchy.any { it.route?.startsWith(route) == true }
    }

    // Navigation items - rol'e göre belirlenir
    val navItems = if (userRole == "moderator") {
        listOf(
            BottomNavItem(
                route = Routes.HOME,
                labelResId = R.string.bottom_nav_home,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavItem(
                route = Routes.PAGE2,
                labelResId = R.string.bottom_nav_page2,
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            BottomNavItem(
                route = Routes.MODERATOR_EVENT,
                labelResId = R.string.bottom_nav_moderator_event,
                selectedIcon = Icons.Filled.Event,
                unselectedIcon = Icons.Outlined.Event
            ),
            BottomNavItem(
                route = Routes.PROFILE,
                labelResId = R.string.bottom_nav_page3,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
    } else {
        listOf(
            BottomNavItem(
                route = Routes.HOME,
                labelResId = R.string.bottom_nav_home,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            ),
            BottomNavItem(
                route = Routes.PAGE2,
                labelResId = R.string.bottom_nav_page2,
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search
            ),
            BottomNavItem(
                route = Routes.PROFILE,
                labelResId = R.string.bottom_nav_page3,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person
            )
        )
    }

    NavigationBar(
        modifier = modifier.height(88.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        navItems.forEach { item ->
            val label = stringResource(id = item.labelResId)
            NavigationBarItem(
                selected = isSelected(item.route),
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected(item.route)) item.selectedIcon else item.unselectedIcon,
                        contentDescription = label
                    )
                },
                label = { Text(label, style = MaterialTheme.typography.labelMedium) },
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
}