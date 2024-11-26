package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prisma.prismavi.viewmodel.color.ColorViewModel

fun parseColor(colorString: String): Color {
    val colorLong = colorString.removePrefix("#").toLong(16)
    return Color(colorLong or 0xFF000000)
}

@Composable
fun ColorDetails(colorViewModel: ColorViewModel) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val sheetHeight = screenHeight * 0.80f
    val colorObject = colorViewModel.colorResponse.observeAsState()
    val colorObjectValue = colorObject.value

    Column(
        modifier = Modifier
            .heightIn(0.dp, sheetHeight)
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        colorObjectValue?.let { color ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(screenWidth * 0.35f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(parseColor(color.hexCode))
            )

            Column(
                modifier = Modifier
                    .height(screenWidth * 0.35f)
                    .padding(start = 15.dp)
            ) {
                    Text(
                        text = color.name,
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
                    Text(text = "HEX: ${color.hexCode}", fontSize = 14.sp)
                    Text(text = "RGB: (${color.rgbCode})", fontSize = 14.sp)
                    Text(text = "RYB: (${color.rybPercentages.r}, ${color.rybPercentages.y}, ${color.rybPercentages.b})", fontSize = 14.sp)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
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
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(11.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(16.dp, 19.dp)
                    ) {
                        Text(
                            text = color.colorTemperature,
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
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(11.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(16.dp, 19.dp)
                    ) {
                        Text(
                            text = color.colorTerminology,
                            fontSize = (screenWidth * 0.04f).value.sp,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
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
                                        .background(parseColor(color.twoHexOfColorsThatMatch[0]))
                                        .padding(10.dp)
                                )

                                Text(
                                    text = color.twoHexOfColorsThatMatch[0],
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(parseColor(color.twoHexOfColorsThatMatch[1]))
                                        .padding(10.dp)
                                )

                                Text(
                                    text = color.twoHexOfColorsThatMatch[1],
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
                    text = color.colorDescription,
                    fontSize = (screenWidth * 0.04f).value.sp,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
    }
}