package com.prisma.prismavi.ui.camera.overlay.bottomsheet

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prisma.prismavi.ui.camera.overlay.bottomsheet.content.BottomSheetBackButton
import com.prisma.prismavi.ui.navigation.bottomsheet.BottomSheetViewManager
import com.prisma.prismavi.viewmodel.ViewModelManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetPreview(
    viewManager: ViewModelManager
) {

    lateinit var bottomSheetViewManager: BottomSheetViewManager
    val bottomSheetHeight: Dp by remember { mutableStateOf(55.dp) }

    val scaffoldState = rememberBottomSheetScaffoldState()
//  val scope = rememberCoroutineScope()


    BottomSheetScaffold(
        sheetContainerColor = Color(0xE6000000),
        sheetContentColor = Color.White,
        scaffoldState = scaffoldState,
        sheetPeekHeight = bottomSheetHeight,
        sheetContent = {
            bottomSheetViewManager = BottomSheetViewManager(viewManager)
            BottomSheetBackButton(bottomSheetViewManager = bottomSheetViewManager)
            bottomSheetViewManager.RenderContent()
        }
    ) {
    }
}

