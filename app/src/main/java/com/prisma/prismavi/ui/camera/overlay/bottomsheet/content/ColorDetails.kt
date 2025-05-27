package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prisma.prismavi.viewmodel.color.ColorViewModel

fun parseColor(colorString: String): Color {
    val colorLong = colorString.removePrefix("#").toLong(16)
    return Color(colorLong or 0xFF000000)
}

//Tela de resposta
@Composable
    fun ColorDetails(colorViewModel: ColorViewModel) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        val sheetHeight = screenHeight * 0.80f
        val colorObject = colorViewModel.colorResponse.observeAsState()
        val color = colorObject.value ?: return

    Column(
        modifier = Modifier
            .heightIn(0.dp, sheetHeight)
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(screenWidth * 0.35f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(parseColor(color.hexCode?: "#000000") )
            )


            Column(
                modifier = Modifier
                    .height(screenWidth * 0.35f)
                    .padding(start = 15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = color.name,
                    fontSize = (screenWidth * 0.045f).value.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ) {
                    Text(text = "HEX: ${color.hexCode}", fontSize = 14.sp)
                    color.rgb?.let {
                        Text(text = "RGB: R=${it.red}, G=${it.green}, B=${it.blue}", fontSize = 14.sp)
                    } ?: Text("RGB: não disponível", fontSize = 14.sp)
                    Text(text = "HSL: ${color.hsl}", fontSize = 14.sp)
                    Text(text = "Luminância: ${"%.3f".format(color.luminance)}", fontSize = 14.sp)
                }
            }
        }

        Text(
            text = "Tags Psicológicas",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),

        ) {
            color.psychologyTags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = tag, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Cores da Paleta",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            color.colorPalette.forEach { item ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(parseColor(item.hex))
                    )
                    Text(text = item.name, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Cor Complementar",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 6.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(parseColor(color.complementaryColor))
            )
            Text(
                text = color.complementaryColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Variações HEX",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),

        ) {
            color.hexVariations.forEach { hex ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(parseColor(hex))
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Categoria da Cor: ${color.colorCategory}",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Sugestões de Uso",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.SemiBold
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .padding(12.dp)
        ) {
            color.designUsageSuggestions.forEach { suggestion ->
                Text(text = "• $suggestion", fontSize = 14.sp, modifier = Modifier.padding(bottom = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Descrição",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.SemiBold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .padding(12.dp)
        ) {
            Text(text = color.description, fontSize = 14.sp)
        }
    }
}