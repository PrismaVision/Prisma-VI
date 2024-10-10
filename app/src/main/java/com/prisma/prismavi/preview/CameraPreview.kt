package com.prisma.prismavi.preview

import android.content.Context
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import java.io.File

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val previewView = PreviewView(context)

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var capturedImageUri: Uri? by remember { mutableStateOf(null) }

    if (capturedImageUri != null) {
        // Exibe o preview da imagem capturada com o botão "X"
        ImagePreview(imageUri = capturedImageUri!!) {
            // Ao clicar no botão "X", reseta o estado para voltar para a câmera
            capturedImageUri = null
        }
    } else {
        // Exibe a visualização da câmera
        AndroidView(factory = {
            previewView
        }, modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { offset ->
                    capturePhoto(imageCapture, context, offset) { uri ->
                        capturedImageUri = uri
                    }
                })
            })

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context as LifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                // Handle exceptions
            }
        }, ContextCompat.getMainExecutor(context))
    }
}
private fun capturePhoto(
    imageCapture: ImageCapture?,
    context: Context,
    offset: Offset,
    onImageSaved: (Uri) -> Unit
) {
    val photoFile = createFile(context)
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture?.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
            Toast.makeText(context, "Foto salva em: $savedUri", Toast.LENGTH_SHORT).show()
            onImageSaved(savedUri)
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
