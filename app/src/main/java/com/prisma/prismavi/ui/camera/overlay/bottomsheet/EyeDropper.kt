package com.prisma.prismavi.ui.camera.overlay.bottomsheet

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.prisma.prismavi.R

@Composable
fun EyeDropperScreen() {
    val screenSize = remember { mutableStateOf(Size.Zero) }
    val squarePosition = remember { mutableStateOf(Offset.Zero) }
    val squareSize = 45.dp
    val squareColor = remember { mutableStateOf(Color.Transparent) }
    val density = LocalDensity.current
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    LaunchedEffect(squarePosition.value) {
        kotlinx.coroutines.delay(700)
        if (squarePosition.value != Offset.Zero) {
            Toast.makeText(
                context,
                "${squareColor.value}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Box(modifier = Modifier.
        fillMaxSize().
        onGloballyPositioned { coordinates ->
        screenSize.value = coordinates.size.toSize()
    }) {
        Image(
            painter = painterResource(id = R.drawable.prismalogo),
            contentDescription = "Descrição da imagem",
        )
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

                        activity?.window?.let { window ->
                            capturePixelColor(window, squarePosition.value, squarePxSize) { color ->
                                squareColor.value = color
                            }
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
                        .border(color = squareColor.value, width = 6.dp,
                            shape = RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
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

                                activity?.window?.let { window ->
                                    capturePixelColor(window, squarePosition.value, squarePxSize) { color ->
                                        squareColor.value = color
                                    }
                                }
                            }
                        }
                )
            }
        }
    }
    BottomSheetPreview()
}

fun capturePixelColor(
    window: Window,
    position: Offset,
    squarePxSize: Float,
    onColorCaptured: (Color) -> Unit
) {
    val view = window.decorView
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

    val locationInWindow = IntArray(2)
    view.getLocationInWindow(locationInWindow)

    // dont change ->
    val requestRect = Rect(
        (position.x + squarePxSize / 2).toInt(),
        (position.y + squarePxSize).toInt(),
        (position.x + squarePxSize / 2 + 1).toInt(),
        (position.y + squarePxSize + 2 + 10).toInt()
    )

    PixelCopy.request(window, bitmap, { copyResult ->
        if (copyResult == PixelCopy.SUCCESS) {
            val pixelColor = bitmap.getPixel(
                requestRect.centerX().coerceAtMost(bitmap.width - 1),
                requestRect.centerY().coerceAtMost(bitmap.height - 1)
            )
            onColorCaptured(Color(pixelColor))
        }
        bitmap.recycle()
    }, Handler(Looper.getMainLooper()))
}

@Preview
@Composable
fun DragSquareScreenPreview(){
    EyeDropperScreen()
}