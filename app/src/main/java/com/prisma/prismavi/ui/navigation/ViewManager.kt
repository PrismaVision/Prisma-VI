package com.prisma.prismavi.ui.navigation

import LoginScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.prisma.prismavi.core.permissions.PermissionManager
import com.prisma.prismavi.ui.camera.CameraScreen
import com.prisma.prismavi.ui.register.RegisterScreen
import com.prisma.prismavi.ui.splash.SplashScreen
import com.prisma.prismavi.viewmodel.ViewModelManager

class ViewManager(
    activity: ComponentActivity,
    private val permissionManager: PermissionManager,
    private val viewModelManager: ViewModelManager
) {

    private var currentScreen by mutableStateOf<Screen>(Screen.Splash)

    init {
        activity.setContent {
            RenderCurrentScreen()
        }
        navigateTo(Screen.Splash)
    }

    @Composable
    private fun RenderCurrentScreen() {
        when (val screen = currentScreen) {
            is Screen.Splash -> SplashScreen(onSplashFinished = { navigateTo(Screen.Login) })
            is Screen.Camera -> CameraScreen(viewModelManager)
            is Screen.Login -> LoginScreen(viewModelManager.loginViewModel, viewModelManager, onLoginSuccess = { checkAndRequestPermissions() }, backToRegister = { navigateTo(Screen.Register)})
            is Screen.Register -> RegisterScreen(viewModelManager.registerViewModel, viewModelManager) {navigateTo(Screen.Login)}

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
            onStorageDenied = { /*permissionManager.showStoragePermissionDeniedDialog()*/ }
        )
    }
}
