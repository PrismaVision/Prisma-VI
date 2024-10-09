package com.prisma.prismavi.permissions

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.prisma.prismavi.MainActivity

class PermissionManager(private val activity: MainActivity) {

    private val requestCameraPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            activity.onCameraPermissionGranted()
        } else {
            activity.onCameraPermissionDenied()
        }
    }

    private val requestStoragePermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            activity.onStoragePermissionGranted()
        } else {
            activity.onStoragePermissionDenied()
        }
    }

    fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(){
        requestCameraPermission()
        requestStoragePermission()
    }

    fun requestCameraPermission() {
        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    fun requestStoragePermission() {
        requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun showCameraPermissionDeniedDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permissão Necessária")
            .setMessage("A permissão para usar a câmera é necessária para o funcionamento do aplicativo.")
            .setPositiveButton("Tentar Novamente") { _, _ -> requestCameraPermission() }
            .setNegativeButton("Sair") { _, _ -> activity.finish() }
            .show()
    }

    fun showStoragePermissionDeniedDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permissão Necessária")
            .setMessage("A permissão para gravar no armazenamento é necessária para o funcionamento do aplicativo.")
            .setPositiveButton("Tentar Novamente") { _, _ -> requestStoragePermission() }
            .setNegativeButton("Sair") { _, _ -> activity.finish() }
            .show()
    }
}
