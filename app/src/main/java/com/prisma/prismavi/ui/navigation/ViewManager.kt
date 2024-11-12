package com.prisma.prismavi.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.prisma.prismavi.ui.splash.SplashScreen
import com.prisma.prismavi.core.permissions.PermissionManager
import com.prisma.prismavi.ui.camera.CameraScreen

class ViewManager(
    activity: ComponentActivity,
    private val permissionManager: PermissionManager
) {

    private var currentScreen by mutableStateOf<Screen>(Screen.Splash)

    init {
        activity.setContent {
            RenderScreen()
        }
        navigateTo(Screen.Splash)
    }

    @Composable
    private fun RenderScreen() {
        when (currentScreen) {
            is Screen.Splash -> SplashScreen(onSplashFinished = { checkAndRequestPermissions() })
            is Screen.Camera -> CameraScreen(onImageCaptured = { bitmap ->
                // Ações com a imagem capturada (congelada)
            })

            //TODO Adicione outras telas aqui, por exemplo:
            // is Screen.Settings -> SettingsScreen()

        }
    }

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    // Método para verificar e solicitar permissões
    private fun checkAndRequestPermissions() {
        permissionManager.requestPermissions(
            onCameraGranted = { navigateTo(Screen.Camera) },
            onCameraDenied = { permissionManager.showCameraPermissionDeniedDialog() },
            onStorageGranted = { /* pode executar alguma ação específica para armazenamento, se necessário */ },
            onStorageDenied = { permissionManager.showStoragePermissionDeniedDialog() }
        )
    }
}
