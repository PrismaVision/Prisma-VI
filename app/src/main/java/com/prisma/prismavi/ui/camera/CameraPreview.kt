package com.prisma.prismavi.ui.camera

import android.content.Context
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.prisma.prismavi.ui.camera.overlay.bottomsheet.BottomSheetPreview
import java.io.File

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val previewView = PreviewView(context)

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var capturedImageUri: Uri? by remember { mutableStateOf(null) }
    var capturedImageFile: File? by remember { mutableStateOf(null) }

    if (capturedImageUri != null && capturedImageFile != null) {
        ImagePreview(imageUri = capturedImageUri!!, imageFile = capturedImageFile!!) {
            capturedImageFile?.let { file ->
                if (file.exists()) {
                    file.delete()
                    Toast.makeText(context, "Foto excluída: ${file.path}", Toast.LENGTH_SHORT).show()
                }
            }
            capturedImageUri = null
            capturedImageFile = null
        }
    } else {
        AndroidView(factory = {
            previewView
        }, modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { offset ->
                    capturePhoto(imageCapture, context, offset) { uri, file ->
                        capturedImageUri = uri
                        capturedImageFile = file
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
    BottomSheetPreview()
}

private fun capturePhoto(
    imageCapture: ImageCapture?,
    context: Context,
    offset: Offset,
    onImageSaved: (Uri, File) -> Unit
) {
    val photoFile = createFile(context)
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture?.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
            onImageSaved(savedUri, photoFile)
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