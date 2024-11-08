package com.prisma.prismavi.ui.navigation.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.prisma.prismavi.ui.camera.overlay.bottomsheet.content.ColorDetails

class BottomSheetViewManager {
    var currentContent by mutableStateOf<BottomSheetScreen>(BottomSheetScreen.ColorDetails)


    @Composable
    fun RenderContent() {
        when (currentContent) {
            is BottomSheetScreen.ColorDetails -> ColorDetails()

            //TODO Adicione outras telas aqui, por exemplo:
            // is Screen.Settings -> SettingsScreen()

        }
    }

    fun navigateTo(screen: BottomSheetScreen) {
        currentContent = screen
    }
}