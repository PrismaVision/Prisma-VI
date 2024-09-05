package com.prisma.prismavi.permissions

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.prisma.prismavi.MainActivity

class PermissionManager(private val activity: MainActivity) {

    private val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            activity.onPermissionGranted()
        } else {
            activity.onPermissionDenied()
        }
    }

    fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    fun showPermissionDeniedDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permissão Necessária")
            .setMessage("A permissão para usar a câmera é necessária para o funcionamento do aplicativo.")
            .setPositiveButton("Tentar Novamente") { _, _ -> requestCameraPermission() }
            .setNegativeButton("Sair") { _, _ -> activity.finish() }
            .show()
    }
}
