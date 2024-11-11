package com.prisma.prismavi.ui.navigation.bottomsheet

sealed class BottomSheetScreen {
    object ColorDetails : BottomSheetScreen()
    object UserDetails : BottomSheetScreen()
}