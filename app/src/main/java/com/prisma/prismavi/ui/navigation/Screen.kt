package com.prisma.prismavi.ui.navigation

sealed class Screen {
    object Splash : Screen()
    object Camera : Screen()
    // Adicione outras telas conforme necessário, por exemplo:
    // object Settings : Screen()
}