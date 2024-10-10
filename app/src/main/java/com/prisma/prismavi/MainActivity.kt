package com.prisma.prismavi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.prisma.prismavi.camera.CameraManager
import com.prisma.prismavi.permissions.PermissionManager

class MainActivity : ComponentActivity() {

    private var cameraManager: CameraManager? = null
    private var permissionManager: PermissionManager? = null

    private var currentPermissionRequest = 0

    private val permissionsToRequest = arrayOf(
        ::requestCameraPermission,
        ::requestStoragePermission
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraManager = CameraManager(this)
        permissionManager = PermissionManager(this)

        setContent {
            checkAndRequestPermissions()
        }
    }

    private fun checkAndRequestPermissions() {
        if (currentPermissionRequest < permissionsToRequest.size) {
            permissionsToRequest[currentPermissionRequest].invoke()
        } else {
            if (permissionManager!!.isCameraPermissionGranted()) {
                cameraManager!!.startCamera()
            }
        }
    }

    private fun requestCameraPermission() {
        if (!permissionManager!!.isCameraPermissionGranted()) {
            permissionManager!!.requestCameraPermission()
        } else {
            onCameraPermissionGranted()
        }
    }

    private fun requestStoragePermission() {
        if (!permissionManager!!.isStoragePermissionGranted()) {
            permissionManager!!.requestStoragePermission()
        } else {
            onStoragePermissionGranted()
        }
    }

    fun onCameraPermissionGranted() {
        currentPermissionRequest++
        checkAndRequestPermissions()
        cameraManager!!.startCamera()
    }

    fun onCameraPermissionDenied() {
        permissionManager?.showCameraPermissionDeniedDialog()
    }

    fun onStoragePermissionGranted() {
        currentPermissionRequest++
        checkAndRequestPermissions()
    }

    fun onStoragePermissionDenied() {
        permissionManager?.showStoragePermissionDeniedDialog()
    }
}
