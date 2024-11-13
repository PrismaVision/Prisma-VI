package com.prisma.prismavi.ui.camera

import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner

@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner
    val imageCapture = remember { ImageCapture.Builder().build() }
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    if (capturedImage == null) {
        CameraPreview(
            lifecycleOwner = lifecycleOwner,
            imageCapture = imageCapture,
            onCaptureSuccess = { bitmap ->
                capturedImage = bitmap
            }
        )
    } else {
        ImageDisplay(bitmap = capturedImage)
    }
}


