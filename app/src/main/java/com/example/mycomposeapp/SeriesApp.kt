package com.example.mycomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycomposeapp.ui.navigation.ItemOfNavigation
import com.example.mycomposeapp.ui.navigation.Screen
import com.example.mycomposeapp.ui.screen.bookmarked.BookmarkedScreen
import com.example.mycomposeapp.ui.screen.detail.DetailScreen
import com.example.mycomposeapp.ui.screen.home.HomeScreen
import com.example.mycomposeapp.ui.screen.profil.ProfilScreen
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme

@Composable
fun SeriesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { seriesId ->
                        navController.navigate(Screen.Detail.createRoute(seriesId))
                    }
                )
            }
            composable(Screen.Boookmarked.route) {
                BookmarkedScreen(
                    navigateToDetail = { seriesId ->
                        navController.navigate(Screen.Detail.createRoute(seriesId))
                    }
                )
            }
            composable(Screen.Profil.route) {
                ProfilScreen()
            }
            composable(
                Screen.Detail.route,
                arguments = listOf(navArgument("seriesId") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("seriesId") ?: -1
                DetailScreen(
                    seriesId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            ItemOfNavigation(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
                contentDescription = stringResource(R.string.menu_home)
            ),
            ItemOfNavigation(
                title = stringResource(R.string.menu_bookmarked),
                icon = Icons.Default.Favorite,
                screen = Screen.Boookmarked,
                contentDescription = stringResource(R.string.menu_bookmarked)
            ),
            ItemOfNavigation(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.Person,
                screen = Screen.Profil,
                contentDescription = stringResource(R.string.description_profile_user)
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeriesAppPreview() {
    MyComposeAppTheme {
        SeriesApp()
    }
}