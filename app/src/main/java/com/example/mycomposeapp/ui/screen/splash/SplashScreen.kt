package com.example.mycomposeapp.ui.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mycomposeapp.MainActivity
import com.example.mycomposeapp.R
import com.example.mycomposeapp.ui.theme.MyComposeAppTheme

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyComposeAppTheme {
                // Call the SplashContent composable here
                SplashContent()
            }
        }

        // Use Handler to delay navigation to the next screen after 3 seconds
        Handler().postDelayed({
            // Add code to navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Finish the splash screen activity
        }, 3000)
    }
}

@Composable
fun SplashContent() {
    // Use the Box composable to center the content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Change background color to white
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Use Column to place Text below Image
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Add an Image and Text in the center
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.medium)

            )
            Text(
                text = "My Series App",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
    }
}
