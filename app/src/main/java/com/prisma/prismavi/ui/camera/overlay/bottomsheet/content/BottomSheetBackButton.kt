package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prisma.prismavi.ui.navigation.bottomsheet.BottomSheetViewManager

@Composable
fun BottomSheetBackButton(bottomSheetViewManager: BottomSheetViewManager){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { bottomSheetViewManager.backToLast() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp, 0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Fechar",
                tint = Color.White
            )
        }
    }
}