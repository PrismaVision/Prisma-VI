package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun UserDetails() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val sheetHeight = screenHeight * 0.85f

    val username = "Usuário!"
    val colorList = listOf("#000000", "#9AEAC5", "#007CC0")
    var palleteList = listOf("Paleta #1", "Paleta #2", "Paleta #3")

    val onAddPallete: () -> Unit = {
        palleteList += "Pallete${palleteList.size - 1}"
    }

    Column(
        modifier = Modifier
            .height(sheetHeight)
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // Cabeçalho do usuário
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.1f)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Seja Bem Vindo, $username",
                fontSize = (screenWidth * 0.05f).value.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(Modifier.height(screenHeight * 0.02f))

        Row {
            Column {
                Text(
                    text = "Paletas",
                    fontSize = (screenWidth * 0.04f).value.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        for (pallete in palleteList) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(screenHeight * 0.08f)
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(Color.Gray.copy(alpha = 0.3f))
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = pallete,
                                    fontSize = (screenWidth * 0.035f).value.sp,
                                    fontWeight = FontWeight.W600
                                )
                            }
                            Spacer(Modifier.height(screenHeight * 0.01f))
                        }
                        Button(
                            onClick = onAddPallete,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(screenHeight * 0.07f)
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color.Green.copy(alpha = 0.09f)),
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Icon(
                                imageVector = Icons.Sharp.Add,
                                contentDescription = "Add",
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(screenHeight * 0.02f))

        Row {
            Column {
                Text(
                    text = "Histórico de Cores",
                    fontSize = (screenWidth * 0.04f).value.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.01f)
                    ) {
                        for (color in colorList) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(Color.Gray.copy(alpha = 0.3f))
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(screenWidth * 0.08f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(parseColor(color))
                                )
                                Text(
                                    text = color,
                                    fontSize = (screenWidth * 0.035f).value.sp,
                                    fontWeight = FontWeight.W600,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun UserDetailsPreview(){
    UserDetails()
}