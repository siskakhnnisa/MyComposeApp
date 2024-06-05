package com.example.mycomposeapp.di

import com.example.mycomposeapp.data.SeriesRepository

object Injection {

    fun provideRepository(): SeriesRepository {
        return SeriesRepository.getInstance()
    }

}