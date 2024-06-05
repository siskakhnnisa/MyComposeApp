package com.example.mycomposeapp.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ViewModelFactory
import com.example.mycomposeapp.di.Injection
import com.example.mycomposeapp.ui.common.UiState
import com.example.mycomposeapp.ui.components.TextSetting
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme
import com.example.mycomposeapp.ui.theme.GreyLight

@Composable
fun DetailScreen(
    seriesId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                TextSetting(text = stringResource(id = R.string.please_wait))
                viewModel.getSeriesById(seriesId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    title = data.title,
                    genre = data.genre,
                    director = data.director,
                    duration = data.duration,
                    photoUrl = data.photoUrl,
                    description = data.description,
                    isFavourite = data.isBoookmarked,
                    onBookmarkedClick = {
                        viewModel.updateStatusBookmarked(data.id, !data.isBoookmarked)
                    },
                    onBackClick = {
                        navigateBack()
                    }
                )
            }
            is UiState.Error -> {
                TextSetting(text = stringResource(id = R.string.error_message, uiState.errorMessage))
            }
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    genre: String,
    director: String,
    duration: String,
    description: String,
    isFavourite: Boolean,
    photoUrl: String,
    onBookmarkedClick: (status: Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var statusBookmarked by rememberSaveable { mutableStateOf(isFavourite) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = stringResource(id = R.string.detail_series),
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically),
            )
            if (statusBookmarked) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = Color.Red,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            statusBookmarked = false
                            onBookmarkedClick(false)
                        }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            statusBookmarked = true
                            onBookmarkedClick(true)
                        }
                )
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(GreyLight)
            )
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 8.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            ItemDetail(title = "Genre", value = genre)
            ItemDetail(title = "Director", value = director)
            ItemDetail(title = "Duration", value = duration)
            ItemDetail(title = "Description", value = description)
        }
    }
}

@Composable
fun ItemDetail(title: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = value.ifEmpty {
                "-"
            },
            fontSize = 15.sp,
            textAlign = TextAlign.Justify, // Menetapkan alignment rata kanan
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailContentPreview() {
    MyComposeAppTheme {
        DetailContent(
            title = "The Uncanny Counter",
            genre = "Drama, Action",
            director = "Yoo Seon-dong",
            duration = "16 episode",
            description = "This is Drama Serial",
            isFavourite = false,
            photoUrl = "",
            onBookmarkedClick = {},
            onBackClick = {}
        )
    }
}
