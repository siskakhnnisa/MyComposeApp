package com.example.mycomposeapp.model

data class Series (
    val id: Int,
    val title: String,
    val photoUrl: String,
    val genre: String,
    val director: String,
    val duration: String,
    val description: String,
    val isBoookmarked : Boolean = false
)