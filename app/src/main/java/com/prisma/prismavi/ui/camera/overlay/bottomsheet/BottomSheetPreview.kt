package com.prisma.prismavi.ui.camera.overlay.bottomsheet

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.prisma.prismavi.ui.navigation.bottomsheet.BottomSheetViewManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetPreview(
) {

    lateinit var bottomSheetViewManager: BottomSheetViewManager
    val bottomSheetHeight: Dp by remember { mutableStateOf(105.dp) }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = bottomSheetHeight,
        sheetContent = {
            bottomSheetViewManager = BottomSheetViewManager()
            bottomSheetViewManager.RenderContent()

        }
    ) {
    }
}


@Preview
@Composable
fun SheetPreview() {
    BottomSheetPreview()
}