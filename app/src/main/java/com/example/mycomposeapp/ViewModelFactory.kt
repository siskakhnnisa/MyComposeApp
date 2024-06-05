package com.example.mycomposeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycomposeapp.data.SeriesRepository
import com.example.mycomposeapp.ui.screen.bookmarked.BookmarkedViewModel
import com.example.mycomposeapp.ui.screen.detail.DetailViewModel
import com.example.mycomposeapp.ui.screen.home.HomeViewModel
import com.example.mycomposeapp.ui.screen.profil.ProfilViewModel

class ViewModelFactory(private val repository: SeriesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(BookmarkedViewModel::class.java)) {
            return BookmarkedViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            return ProfilViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}