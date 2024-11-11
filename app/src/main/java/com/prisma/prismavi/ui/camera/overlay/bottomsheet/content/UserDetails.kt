package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserDetails(){
    Text(
        text = "UserDetails",
        fontSize = 16.sp,
        modifier = Modifier
            .padding(bottom = 8.dp)
    )
}