package com.prisma.prismavi.ui.tests

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.prisma.prismavi.ui.camera.overlay.bottomsheet.BottomSheetPreview

@Composable
fun DragSquareScreen() {
    val screenSize = remember { mutableStateOf(Size.Zero) }
    val squarePosition = remember { mutableStateOf(Offset.Zero) }
    val squareSize = 55.dp
    val context = LocalContext.current

    LaunchedEffect(squarePosition.value) {
        kotlinx.coroutines.delay(500)
        Toast.makeText(
            context, "Posição do quadrado: (${squarePosition.value.x}, ${squarePosition.value.y})", Toast.LENGTH_SHORT
        ).show()
    }

    Box{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    screenSize.value = coordinates.size.toSize()
                }
                .background(Color.White)
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        val newX = (tapOffset.x - squareSize.toPx() / 2).coerceIn(
                            0f, screenSize.value.width - squareSize.toPx()
                        )
                        val newY = (tapOffset.y - squareSize.toPx() / 2).coerceIn(
                            0f, screenSize.value.height - squareSize.toPx()
                        )
                        squarePosition.value = Offset(newX, newY)
                    }
                }
        ) {
            if(squarePosition.value != Offset.Zero){
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(squarePosition.value.x.toInt(), squarePosition.value.y.toInt())
                        }
                        .size(squareSize)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Blue)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                val newX = (squarePosition.value.x + dragAmount.x).coerceIn(
                                    0f, screenSize.value.width - squareSize.toPx()
                                )
                                val newY = (squarePosition.value.y + dragAmount.y).coerceIn(
                                    0f, screenSize.value.height - squareSize.toPx()
                                )
                                squarePosition.value = Offset(newX, newY)
                            }
                        }
                )
            }
        }
        BottomSheetPreview()
    }
}


@Preview
@Composable
fun DragSquareScreenPreview(){
    DragSquareScreen()
}