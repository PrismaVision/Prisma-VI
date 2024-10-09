package com.prisma.prismavi.camera

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.prisma.prismavi.preview.CameraPreview

class CameraManager(private val activity: ComponentActivity) {



    fun startCamera() {
        activity.setContent {
            CameraPreview()
        }
    }
}
