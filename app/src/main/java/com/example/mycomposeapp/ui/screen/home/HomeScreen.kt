package com.example.mycomposeapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ViewModelFactory
import com.example.mycomposeapp.di.Injection
import com.example.mycomposeapp.model.Series
import com.example.mycomposeapp.ui.common.UiState
import com.example.mycomposeapp.ui.components.ItemSeries
import com.example.mycomposeapp.ui.components.TextSetting

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    // Menambahkan searchQuery sebagai mutable state di dalam HomeScreen
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                viewModel.searchSeries(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text(text = "Search a series here")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Perform any action on Done pressed, if needed */ }
            )
        )

        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    TextSetting(text = stringResource(id = R.string.please_wait))
                    viewModel.getAllSeries()
                }
                is UiState.Success -> {
                    val seriesList = uiState.data
                    val trimmedQuery = searchQuery.value.trim()
                    val filteredSeries = if (trimmedQuery.isNotEmpty()) {
                        seriesList.filter { data ->
                            data.title.contains(trimmedQuery, ignoreCase = true)
                        }
                    } else {
                        seriesList
                    }

                    if (filteredSeries.isEmpty()) {
                        Text(
                            text = stringResource(R.string.data_empty),
                            modifier = Modifier.padding(top = 100.dp, start = 140.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 17.sp
                        )
                    } else {
                        HomeContent(
                            series = filteredSeries,
                            modifier = modifier,
                            navigateToDetail = navigateToDetail,
                            searchQuery = searchQuery.value
                        )
                    }
                }


                is UiState.Error -> {
                    TextSetting(text = stringResource(id = R.string.error_message, uiState.errorMessage))
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    series: List<Series>,
    modifier: Modifier,
    navigateToDetail: (Int) -> Unit,
    searchQuery: String // Menambahkan searchQuery sebagai parameter
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(series) { data ->
            if (data.title.contains(searchQuery, ignoreCase = true)) {
                ItemSeries(
                    title = data.title,
                    photoUrl = data.photoUrl,
                    genre = data.genre, // Menambahkan nama parameter ke data.genre
                    duration = data.duration, // Menambahkan nama parameter ke data.duration
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}
