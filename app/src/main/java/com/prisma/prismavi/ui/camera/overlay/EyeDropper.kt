package com.prisma.prismavi.ui.camera.overlay

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.prisma.prismavi.viewmodel.ViewModelManager

@Composable
fun EyeDropperScreen(bitmap: Bitmap, viewModelManager: ViewModelManager) {
    val screenSize = remember { mutableStateOf(Size.Zero) }
    val squarePosition = remember { mutableStateOf(Offset.Zero) }
    val squareSize = 45.dp
    val squareColor = remember { mutableStateOf(Color.Transparent) }
    val density = LocalDensity.current
    val context = LocalContext.current

    fun invertColor(color: Color): Color {
        val red = (255 - (color.red * 255)).toInt()
        val green = (255 - (color.green * 255)).toInt()
        val blue = (255 - (color.blue * 255)).toInt()
        return Color(red, green, blue)
    }
    fun colorToHexString(color: Color): String {
        return String.format("#%08X", color.toArgb())
    }

    LaunchedEffect(squarePosition.value) {
        kotlinx.coroutines.delay(700)
        if (squarePosition.value != Offset.Zero) {
            Toast.makeText(
                context,
                colorToHexString(squareColor.value),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                screenSize.value = coordinates.size.toSize()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        val squarePxSize = with(density) { squareSize.toPx() }
                        val newX = (tapOffset.x - squarePxSize / 2).coerceIn(
                            0f, screenSize.value.width - squarePxSize
                        )
                        val newY = (tapOffset.y - squarePxSize / 2).coerceIn(
                            0f, screenSize.value.height - squarePxSize
                        )
                        squarePosition.value = Offset(newX, newY)

                        capturePixelColor(bitmap, squarePosition.value, squarePxSize) { color ->
                            squareColor.value = color
                        }
                    }
                }
        ) {
            if (squarePosition.value != Offset.Zero) {
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                squarePosition.value.x.toInt(),
                                squarePosition.value.y.toInt()
                            )
                        }
                        .size(squareSize)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            color = invertColor(squareColor.value),
                            width = 4.dp,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(squareColor.value)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                val squarePxSize = with(density) { squareSize.toPx() }
                                val newX = (squarePosition.value.x + dragAmount.x).coerceIn(
                                    0f, screenSize.value.width - squarePxSize
                                )
                                val newY = (squarePosition.value.y + dragAmount.y).coerceIn(
                                    0f, screenSize.value.height - squarePxSize
                                )
                                squarePosition.value = Offset(newX, newY)

                                capturePixelColor(bitmap, squarePosition.value, squarePxSize) { color ->
                                    squareColor.value = color
                                }
                            }
                        }
                )
            }
        }
    }
}

fun capturePixelColor(
    bitmap: Bitmap,
    position: Offset,
    squarePxSize: Float,
    onColorCaptured: (Color) -> Unit
) {
    val centerX = (position.x + squarePxSize / 2).toInt().coerceIn(0, bitmap.width - 1)
    val centerY = (position.y + squarePxSize / 2).toInt().coerceIn(0, bitmap.height - 1)
    val pixelColor = bitmap.getPixel(centerX, centerY)
    onColorCaptured(Color(pixelColor))
}

