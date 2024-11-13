package com.prisma.prismavi.ui.camera

import android.annotation.SuppressLint
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import java.io.File
import kotlin.math.roundToInt

@SuppressLint("ClickableViewAccessibility")
@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageFile by remember { mutableStateOf<File?>(null) }
    var touchPosition by remember { mutableStateOf<Pair<Float, Float>?>(null) }
    var boxOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var isImagePreviewActive by remember { mutableStateOf(false) }

    val previewView = remember { PreviewView(context) }

    LaunchedEffect(context) {
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()

        val previewUseCase = Preview.Builder().build().apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

        val imageCapture = ImageCapture.Builder().build()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase,
                imageCapture
            )

            previewView.setOnTouchListener { _, event ->
                val x = event.x
                val y = event.y
                touchPosition = Pair(x, y)

                capturePhoto(imageCapture, context) { uri, file ->
                    capturedImageUri = uri
                    capturedImageFile = file
                    isImagePreviewActive = true

                    touchPosition?.let { (xTouch, yTouch) ->
                        boxOffset = Offset(xTouch - 50f, yTouch - 50f)
                    }
                }
                true
            }

        } catch (exc: Exception) {
            Toast.makeText(context, "Erro ao inicializar a câmera: ${exc.message}", Toast.LENGTH_SHORT).show()
        }
    }

    if (isImagePreviewActive) {
        capturedImageUri?.let { uri ->
            capturedImageFile?.let { file ->
                ImagePreview(
                    imageUri = uri,
                    imageFile = file,
                    boxOffset = boxOffset,
                    onBoxMoved = { newOffset -> boxOffset = newOffset },
                    onClose = {
                        isImagePreviewActive = false
                        capturedImageUri = null
                        capturedImageFile = null
                    }
                )
            }
        }
    } else {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
    }
}

private fun capturePhoto(
    imageCapture: ImageCapture,
    context: Context,
    onImageSaved: (Uri, File) -> Unit
) {
    val photoFile = createFile(context)
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
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

@Composable
fun ImagePreview(imageUri: Uri, imageFile: File, boxOffset: Offset, onBoxMoved: (Offset) -> Unit, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .offset { IntOffset(boxOffset.x.roundToInt(), boxOffset.y.roundToInt()) }
                .size(50.dp)
                .background(Color.Red.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        onBoxMoved(boxOffset + dragAmount)
                    }
                }
                .clip(RoundedCornerShape(15.dp))
        )

        IconButton(
            onClick = {
                onClose()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Fechar",
                tint = Color.White
            )
        }
    }
}
