package com.prisma.prismavi.ui.camera

import android.content.Context
import android.net.Uri
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.prisma.prismavi.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.ComponentActivity

@Composable
fun CameraPreview(
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val context = LocalContext.current
    val imageCapture = remember { ImageCapture.Builder().build() }
    val outputDirectory = getOutputDirectory(context)

    AndroidView(
        factory = { ctx ->
            val previewView = androidx.camera.view.PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        ctx as ComponentActivity,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                } catch (e: Exception) {
                    Log.e("CameraPreview", "Error binding camera", e)
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView.apply {
                setOnClickListener {
                    capturePhoto(
                        imageCapture = imageCapture,
                        outputDirectory = outputDirectory,
                        context = context,
                        onImageCaptured = onImageCaptured,
                        onError = onError
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize().clickable {  }
    )
}

private fun capturePhoto(
    imageCapture: ImageCapture,
    outputDirectory: File,
    context: Context,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCaptured(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraPreview", "Image capture failed", exception)
                onError(exception)
            }
        }
    )
}

private fun getOutputDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

