package com.prisma.prismavi.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prisma.prismavi.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF000000), Color(0xFF666666)),
    )

    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.DarkGray.copy(alpha = 0.7f), shape = RoundedCornerShape(25.dp))
            )

            Image(
                painter = painterResource(id = R.drawable.prismalogo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)  // Tamanho da imagem
            )
        }

    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onSplashFinished = {})
}