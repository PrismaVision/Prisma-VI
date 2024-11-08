package com.prisma.prismavi.core.permissions

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity

class PermissionManager(private val activity: ComponentActivity) {

    private var onCameraPermissionGranted: (() -> Unit)? = null
    private var onCameraPermissionDenied: (() -> Unit)? = null

    private var onStoragePermissionGranted: (() -> Unit)? = null
    private var onStoragePermissionDenied: (() -> Unit)? = null

    private val requestCameraPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onCameraPermissionGranted?.invoke()
        } else {
            onCameraPermissionDenied?.invoke()
        }
    }

    private val requestStoragePermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onStoragePermissionGranted?.invoke()
        } else {
            onStoragePermissionDenied?.invoke()
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

    fun requestPermissions(
        onCameraGranted: () -> Unit,
        onCameraDenied: () -> Unit,
        onStorageGranted: () -> Unit,
        onStorageDenied: () -> Unit
    ) {
        // Define os callbacks para serem usados nas permissões
        onCameraPermissionGranted = onCameraGranted
        onCameraPermissionDenied = onCameraDenied
        onStoragePermissionGranted = onStorageGranted
        onStoragePermissionDenied = onStorageDenied

        // Solicita as permissões
        requestCameraPermission()
        requestStoragePermission()
    }

    private fun requestCameraPermission() {
        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun requestStoragePermission() {
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
            .setNegativeButton("Ok") { _, _ -> null }
            .show()
    }
}
