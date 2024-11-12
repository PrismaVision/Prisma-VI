package com.prisma.prismavi.ui.camera


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun CameraScreen(onImageCaptured: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = context as LifecycleOwner
    val imageCapture = remember { ImageCapture.Builder().build() }
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    if (capturedImage != null) {
        Image(bitmap = capturedImage!!.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize())
    } else {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(surfaceProvider)
                        }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner, cameraSelector, preview, imageCapture
                            )
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }, ContextCompat.getMainExecutor(ctx))
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    // Captura a imagem no toque
                    imageCapture.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(imageProxy: ImageProxy) {
                                val bitmap = imageProxy.toBitmap() // Função que converte para Bitmap
                                imageProxy.close()
                                capturedImage = bitmap
                                onImageCaptured(bitmap)
                            }
                            override fun onError(exception: ImageCaptureException) {
                                // Handle error
                            }
                        }
                    )
                }
        )
    }
}

// Função auxiliar para converter ImageProxy em Bitmap
fun ImageProxy.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

