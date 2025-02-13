package com.example.perplexity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.perplexity.ui.theme.PerplexityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerplexityTheme {
                Box(
                    Modifier
                        .background(Color(0xFF121212))
                        .safeDrawingPadding()
                ) {
                    PerplexityScreen()
                }
            }
        }
    }
}