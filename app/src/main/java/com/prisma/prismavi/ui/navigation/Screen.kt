package com.prisma.prismavi.ui.navigation

import android.net.Uri
import java.io.File

sealed class Screen {
    object Splash : Screen()
    object Camera : Screen()
    data class FrozenImage(val imageUri: Uri, val imageFile: File, val onClose: () -> Unit) : Screen()
    // Adicione outras telas conforme necessário, por exemplo:
    // object Settings : Screen()
}