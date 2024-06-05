package com.example.mycomposeapp.data

import com.example.mycomposeapp.model.Profil
import com.example.mycomposeapp.model.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SeriesRepository {

    private val series = mutableListOf<Series>()

    init {
        if (series.isEmpty()) {
            SeriesData.dummySeries.forEach {
                series.add(it)
            }
        }
    }

    fun getAllSeries(): Flow<List<Series>> {
        return flowOf(series)
    }

    fun getBookmarkedSeries(): Flow<List<Series>> {
        return getAllSeries()
            .map { series ->
                series.filter { series ->
                    series.isBoookmarked
                }
            }
    }

    fun getSeriesById(seriesId: Int): Series {
        return series.first {
            it.id == seriesId
        }
    }

    fun getProfil(): Profil {
        return Profil(
            name = "Siska Khoirunnisa",
            photoUrl = "https://raw.githubusercontent.com/SiskaKhoirunnisa/gambarajasi/main/siska.jpg",
            email = "siskakhoirunnisa00@gmail.com"
        )
    }

    fun updateStatusFavorite(seriesId: Int, status: Boolean): Flow<Boolean> {
        val index = series.indexOfFirst { it.id == seriesId }
        val result = if (index >= 0) {
            val serial = series[index]
            series[index] =
                serial.copy(isBoookmarked = status)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun searchSeries(query: String): List<Series>{
        return SeriesData.dummySeries.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: SeriesRepository? = null

        fun getInstance(): SeriesRepository =
            instance ?: synchronized(this) {
                SeriesRepository().apply {
                    instance = this
                }
            }
    }

}