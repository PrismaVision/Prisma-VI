package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState

fun parseColor(colorString: String): Color {
    val colorLong = colorString.removePrefix("#").toLong(16)
    return Color(colorLong or 0xFF000000)
}

val colorName: String = "Nome da Cor"
val colorTemperatureName: String = "Quente"
val hexCode: String = "FFFFFF"
val formatedHexCode: Color = parseColor(hexCode)
val colorHex: String = "HEX: #$hexCode"
val colorRgb: String = "RGB: (0, 0, 0)"
val colorRyb: String = "RYB: (0%, 0%, 0%)"
val descriptionText: String = "A cor $colorTemperatureName incorpora uma tonalidade intensa e vibrante que evoca sensações de calor e energia. Ele irradia uma essência ousada e ardente, que lembra um pôr do sol escaldante ou brasas brilhantes. Este tom cativante capta a atenção e desperta a paixão, tornando-o perfeito para designs que visam inspirar excitação e vitalidade.\""
val colorTerminology: String = "Primária"
val colorMatch1: String = "#000000"
val colorMatch2: String = "#000000"


@Composable
fun ColorDetails() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val sheetHeight = screenHeight * 0.80f

    Column(
        modifier = Modifier
            .height(sheetHeight)
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.2f)
                .padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(screenWidth * 0.35f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(parseColor("#$hexCode"))
            )

            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = colorName,
                    fontSize = (screenWidth * 0.04f).value.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Text(text = colorHex, fontSize = 14.sp)
                    Text(text = colorRgb, fontSize = 14.sp)
                    Text(text = colorRyb, fontSize = 14.sp)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Temperatura da Cor",
                        fontSize = (screenWidth * 0.04f).value.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .height(screenHeight * 0.1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = colorTemperatureName,
                            fontSize = (screenWidth * 0.04f).value.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }

                    Text(
                        text = "Terminologia da Cor",
                        fontSize = (screenWidth * 0.04f).value.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .height(screenHeight * 0.1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = colorTerminology,
                            fontSize = (screenWidth * 0.04f).value.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(screenHeight * 0.28f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Cores Correspondentes",
                        fontSize = (screenWidth * 0.04f).value.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(10.dp).fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(parseColor(colorMatch1))
                                        .padding(10.dp)
                                )

                                Text(
                                    text = colorMatch1,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(parseColor(colorMatch2))
                                        .padding(10.dp)
                                )

                                Text(
                                    text = colorMatch2,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Descrição",
                fontSize = (screenWidth * 0.04f).value.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .padding(16.dp)
            ) {
                Text(
                    text = descriptionText,
                    fontSize = (screenWidth * 0.04f).value.sp,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
}

@Preview
@Composable
fun ColorDetailsPreview() {
    ColorDetails()
}