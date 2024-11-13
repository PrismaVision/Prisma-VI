package com.prisma.prismavi.ui.camera

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner,
    imageCapture: ImageCapture,
    onCaptureSuccess: (Bitmap) -> Unit
) {
    val context = LocalContext.current // Aqui pegamos o contexto da aplicação ou activity

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context) // Usando LocalContext.current aqui
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
                }, ContextCompat.getMainExecutor(context))
            }
        },
        modifier = Modifier.fillMaxSize().clickable {
            imageCapture.takePicture(
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(imageProxy: ImageProxy) {
                        val bitmap = imageProxy.toBitmap() // Função que converte para Bitmap
                        imageProxy.close()
                        onCaptureSuccess(bitmap)
                    }
                    override fun onError(exception: ImageCaptureException) {
                        // Handle error
                    }
                }
            )
        }
    )
}





