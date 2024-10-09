package com.prisma.prismavi.preview

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import android.widget.Toast
import androidx.camera.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import java.io.File

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val previewView = PreviewView(context)

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    AndroidView(factory = {
        previewView
    }, modifier = Modifier.fillMaxSize()
        .pointerInput(Unit){
            detectTapGestures(onTap = { offset ->
                capturePhoto(imageCapture, context, offset)
            })
        })


    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        imageCapture = ImageCapture.Builder().build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                context as ComponentActivity,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (exc: Exception) {
            // Handle exceptions
        }
    }, ContextCompat.getMainExecutor(context))
}

private fun capturePhoto(imageCapture: ImageCapture?, context: Context, offset: Offset) {
    val photoFile = createFile(context)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    imageCapture?.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
            Toast.makeText(context, "Foto salva em: $savedUri", Toast.LENGTH_SHORT).show()
            // Você pode usar a posição do toque se necessário
            // offset.x e offset.y
        }

        override fun onError(exception: ImageCaptureException) {
            Toast.makeText(context, "Erro ao salvar a foto: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

private fun createFile(context: Context): File {
    val directory = context.getExternalFilesDir(DIRECTORY_PICTURES)
    return File(directory, "${System.currentTimeMillis()}.jpg")
}
