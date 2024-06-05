package com.example.mycomposeapp.ui.screen.profil

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mycomposeapp.ViewModelFactory
import com.example.mycomposeapp.di.Injection
import com.example.mycomposeapp.ui.common.UiState
import com.example.mycomposeapp.ui.components.TextSetting
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme
import com.example.mycomposeapp.R

@Composable
fun ProfilScreen(
    modifier: Modifier = Modifier,
        viewModel: ProfilViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                TextSetting(text = stringResource(id = R.string.please_wait))
                viewModel.getAbout()
            }
            is UiState.Success -> {
                val data = uiState.data
                ProfilContent(
                    name = data.name,
                    photoUrl = data.photoUrl,
                    email = data.email
                )
            }
            is UiState.Error -> {
                TextSetting(text = stringResource(id = R.string.error_message, uiState.errorMessage))
            }
        }
    }
}

@Composable
fun ProfilContent(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    email: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter // Ubah dari Center ke TopCenter
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                name, fontSize = 18.sp, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                email, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutContentPreview() {
    MyComposeAppTheme {
        ProfilContent(
            name = "Siska Khoirunnisa",
            photoUrl = "https://raw.githubusercontent.com/SiskaKhoirunnisa/gambarajasi/main/siska.jpg",
            email = "siskakhoirunnisa00@gmail.com"
        )
    }
}
