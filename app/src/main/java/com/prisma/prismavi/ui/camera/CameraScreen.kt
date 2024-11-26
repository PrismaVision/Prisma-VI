package com.prisma.prismavi.ui.camera

import android.graphics.Bitmap
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.prisma.prismavi.ui.camera.overlay.bottomsheet.BottomSheetPreview

@Composable
fun CameraScreen() {

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var previewView: PreviewView? by remember { mutableStateOf(null) }
    Box{


    Box(modifier = Modifier.fillMaxSize()) {
        if (capturedBitmap != null) {
            CapturedImagePreview(
                bitmap = capturedBitmap!!,
                onDelete = {
                    capturedBitmap = null
                }
            )
        } else {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onPreviewViewReady = { preview ->
                    previewView = preview
                }
            )
        }
    }

    if (previewView != null && capturedBitmap == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        previewView?.bitmap?.let { bitmap ->
                            capturedBitmap = bitmap.config?.let { it1 -> bitmap.copy(it1, true) }
                        }
                    }
                }
            )
        }
        BottomSheetPreview()
    }
}







