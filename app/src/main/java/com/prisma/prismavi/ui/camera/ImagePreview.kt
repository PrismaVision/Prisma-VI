package com.prisma.prismavi.ui.camera

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import com.prisma.prismavi.ui.camera.overlay.bottomsheet.EyeDropperScreen

@Composable
fun CapturedImagePreview(
    bitmap: Bitmap,
    onDelete: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Imagem Capturada",
            modifier = Modifier.fillMaxSize()
        )

        EyeDropperScreen()

        IconButton(
            onClick = { onDelete() },
            content = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = Color.White
                )
            },
            modifier = Modifier
                .align(Alignment.TopStart)
        )
    }
}



