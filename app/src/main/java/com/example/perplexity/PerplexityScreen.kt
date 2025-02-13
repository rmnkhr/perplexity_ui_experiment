package com.example.perplexity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerplexityScreen() {
    val darkBackground = Color(0xFF121212)
    val grayText = Color(0xFF888888)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Top bar with title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_avatar),
                contentDescription = "Home",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "perplexity",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White
            )
        }

        // Center content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PerplexityLogo(
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Where",
                color = grayText,
                fontSize = 28.sp
            )
            Text(
                text = "knowledge",
                color = grayText,
                fontSize = 28.sp
            )
            Text(
                text = "begins",
                color = grayText,
                fontSize = 28.sp
            )
        }

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            // News ticker/carousel
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(3) { index ->
                    Card(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1E1E1E)
                        )
                    ) {
                        when (index) {
                            0 -> WeatherItem()
                            1 -> NewsItem()
                            2 -> AppUpdateItem()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search input field
            TextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Ask anything...") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF1E1E1E),
                    focusedContainerColor = Color(0xFF1E1E1E),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(36.dp),
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_camera),
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_mic),
                        contentDescription = "Voice input",
                        tint = Color.White
                    )
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = grayText
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = grayText
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = grayText
                )
            }
        }
    }
}

@Composable
private fun WeatherItem() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_sun),
            contentDescription = "Weather",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text("1°C Sunny", color = Color.White)
    }
}

@Composable
private fun NewsItem() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Musk-Led Group Bids for OpenAI",
            color = Color.White
        )
    }
}

@Composable
private fun AppUpdateItem() {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "App Update Available",
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerplexityScreenPreview() {
    PerplexityScreen()
}