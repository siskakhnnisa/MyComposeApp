package com.example.mycomposeapp.ui.screen.bookmarked

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlaceholderVerticalAlign.Companion.TextCenter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ViewModelFactory
import com.example.mycomposeapp.di.Injection
import com.example.mycomposeapp.model.Series
import com.example.mycomposeapp.ui.common.UiState
import com.example.mycomposeapp.ui.components.ItemSeries
import com.example.mycomposeapp.ui.components.TextSetting

@Composable
fun BookmarkedScreen(
    modifier: Modifier = Modifier,
    viewModel: BookmarkedViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                TextSetting(text = stringResource(id = R.string.please_wait))
                viewModel.getBookmarkedSeries()
            }
            is UiState.Success -> {
                FavoriteContent(
                    series = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                TextSetting(text = stringResource(id = R.string.error_message, uiState.errorMessage))
            }
        }
    }
}

@Composable
fun FavoriteContent(
    series: List<Series>,
    modifier: Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    if (series.isEmpty()){
        TextSetting(text = stringResource(id = R.string.data_empty, "Bookmarked Series"))
    }else{
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(series) { data ->
                ItemSeries(
                    title = data.title,
                    photoUrl = data.photoUrl,
                    data.genre, data.duration,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}