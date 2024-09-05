package com.prisma.prismavi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.prisma.prismavi.camera.CameraManager
import com.prisma.prismavi.permissions.PermissionManager

class MainActivity : ComponentActivity() {

    private var cameraManager: CameraManager? = null
    private var permissionManager: PermissionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraManager = CameraManager(this)
        permissionManager = PermissionManager(this)

        setContent {
            if (permissionManager!!.isCameraPermissionGranted()) {
                cameraManager!!.startCamera()
            } else {
                permissionManager!!.requestCameraPermission()
            }
        }
    }

    fun onPermissionGranted() {
        cameraManager?.startCamera()
    }

    fun onPermissionDenied() {
        permissionManager?.showPermissionDeniedDialog()
    }
}
