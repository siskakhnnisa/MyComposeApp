package com.example.mycomposeapp.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class ItemOfNavigation (
    val title: String,
    val icon: ImageVector,
    val screen: Screen,
    val contentDescription: String
)