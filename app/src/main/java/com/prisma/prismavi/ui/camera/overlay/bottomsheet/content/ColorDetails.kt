package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

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
val colorMatch2: String = "#111111"

@Composable
fun ColorDetails() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ){

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(formatedHexCode)
        )

        Column(
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = colorName,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 8.dp) // Espaçamento abaixo do título
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(15.dp)) // Define a forma arredondada dos cantos
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .padding(16.dp)
                    // Espaçamento interno

            ) {
                Row(){
                    Text(
                        text = colorHex,
                        fontSize = 14.sp // Tamanho padrão
                    )
                }
                Row(){
                    Text(
                        text = colorRgb,
                        fontSize = 14.sp// Tamanho menor do texto
                    )
                }
                Row(){
                    Text(
                        text = colorRyb,
                        fontSize = 14.sp // Tamanho menor do texto
                    )
                }

            }
        }
    }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp) // Preenchimento interno acima da coluna
        ) {
            // Linha contendo "Color Temperature" e "Color Match"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Coluna para "Color Temperature" e "Color Terminology"
                Column(
                    modifier = Modifier
                        .weight(0.9f) // Faz a coluna ocupar metade do espaço disponível
                        .padding(end = 8.dp) // Espaçamento à direita
                ) {
                    Text(
                        text = "Temperatura da Cor",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )

                    // Box para "Color Temperature" (sua temperatura de cor)
                    Box(
                        modifier = Modifier
                            .height(70.dp) // Altura do Box
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp)) // Define a forma arredondada dos cantos
                            .background(Color.Gray.copy(alpha = 0.3f)) // Aplica o background cinza
                            .padding(16.dp) // Espaçamento interno
                    ) {
                        Text(
                            text = colorTemperatureName,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.CenterStart) // Alinha o texto à esquerda
                        )
                    }

                    // Texto "Color Terminology"
                    Text(
                        text = "Terminologia da Cor",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    )

                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = colorTerminology,
                            fontSize = 16.sp, // Tamanho padrão
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(203.dp)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Cores Correspondentes",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Start)
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(10.dp)
                                .fillMaxHeight(),
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
                                        .background(formatedHexCode)
                                        .padding(10.dp)
                                )

                                Text(
                                    text = colorMatch1, 
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(start = 6.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(25.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(formatedHexCode)
                                        .padding(10.dp)
                                )

                                Text(
                                    text = colorMatch2,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .padding(start = 6.dp)
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
                .padding(top = 8.dp), // Preenchimento interno acima da coluna
        ) {
            // Texto acima da Column de detalhes
            Text(
                text = "Descrição",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp) // Espaçamento abaixo do texto
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp)) // Define a forma arredondada dos cantos
                    .background(Color.Gray.copy(alpha = 0.3f)) // Aplica o background cinza
                    .padding(16.dp) // Espaçamento interno
            ) {
                // Colocando o texto alinhado à esquerda e centralizado verticalmente
                Text(
                    text = descriptionText,
                    fontSize = 16.sp, // Tamanho padrão
                    modifier = Modifier
                        .align(Alignment.CenterStart) // Alinha o texto à esquerda, mas centraliza verticalmente
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