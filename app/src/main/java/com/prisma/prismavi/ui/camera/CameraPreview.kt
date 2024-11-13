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

    // Estado para armazenar a URI e o arquivo da foto capturada
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageFile by remember { mutableStateOf<File?>(null) }

    // Estado para armazenar a posição do toque na tela
    var touchPosition by remember { mutableStateOf<Pair<Float, Float>?>(null) }

    // Estado para controlar a posição inicial da box arrastável
    var boxOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    // Estado para controlar se a imagem está sendo exibida
    var isImagePreviewActive by remember { mutableStateOf(false) }

    // Preview da câmera (view da câmera)
    val previewView = remember { PreviewView(context) }

    // Inicializa a câmera e captura a foto dentro do LaunchedEffect
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

            // Detecta o toque na tela para capturar a foto
            previewView.setOnTouchListener { _, event ->
                // Captura a foto e armazena a posição do toque
                val x = event.x
                val y = event.y

                // Armazena a posição do toque para posicionar o box
                touchPosition = Pair(x, y)

                // Chama a função para capturar a foto
                capturePhoto(imageCapture, context) { uri, file ->
                    capturedImageUri = uri
                    capturedImageFile = file

                    // Quando a imagem é capturada, mostramos a imagem
                    isImagePreviewActive = true

                    // Define a posição do box com base no toque (centro do toque)
                    touchPosition?.let { (xTouch, yTouch) ->
                        // Coloca a box no centro do ponto de toque
                        boxOffset = Offset(xTouch - 50f, yTouch - 50f) // Subtrai a metade do tamanho da box
                    }
                }
                true
            }

        } catch (exc: Exception) {
            Toast.makeText(context, "Erro ao inicializar a câmera: ${exc.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Se a imagem está sendo visualizada, exibe a composable da imagem
    if (isImagePreviewActive) {
        capturedImageUri?.let { uri ->
            capturedImageFile?.let { file ->
                // Exibe a imagem e o box arrastável
                ImagePreview(
                    imageUri = uri,
                    imageFile = file,
                    boxOffset = boxOffset,
                    onBoxMoved = { newOffset ->
                        // Atualiza a posição do box com base no movimento de arraste
                        boxOffset = newOffset
                    },
                    onClose = {
                        // Resetando o estado ao fechar a imagem
                        isImagePreviewActive = false
                        capturedImageUri = null
                        capturedImageFile = null
                    }
                )
            }
        }
    } else {
        // Caso contrário, exibe o preview da câmera
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
    }
}

// Função para capturar a foto
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

// Função para criar o arquivo da foto
private fun createFile(context: Context): File {
    val directory = context.getExternalFilesDir(DIRECTORY_PICTURES)
    return File(directory, "${System.currentTimeMillis()}.jpg")
}



// Composable para exibir a imagem com o box arrastável
@Composable
fun ImagePreview(imageUri: Uri, imageFile: File, boxOffset: Offset, onBoxMoved: (Offset) -> Unit, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Exibe a imagem capturada
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Box arrastável (pode ser um quadrado, círculo, ou qualquer forma)
        Box(
            modifier = Modifier
                .offset { IntOffset(boxOffset.x.roundToInt(), boxOffset.y.roundToInt()) }
                .size(50.dp) // Defina o tamanho da box (100x100 dp por exemplo)
                .background(Color.Red.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        // Atualiza a posição do box com base no movimento de arraste
                        onBoxMoved(boxOffset + dragAmount)
                    }
                }
                .clip(RoundedCornerShape(15.dp))
        )

        // Botão para fechar a visualização da imagem e voltar para a câmera
        IconButton(
            onClick = {
                // Resetando o estado ao fechar a imagem
                onClose()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close, // Ícone de fechar da biblioteca de ícones do Compose
                contentDescription = "Fechar",
                tint = Color.White
            )
        }
    }
}



