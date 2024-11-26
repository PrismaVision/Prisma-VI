package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.zIndex
import com.prisma.prismavi.ui.navigation.Screen

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
//
        Spacer(Modifier.height(screenHeight * 0.02f))

        // Help Section
        Text(
            text = "Ajuda",
            fontSize = (screenWidth * 0.04f).value.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            )
        )
        var isManualVisible by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {

            Box(
                modifier = Modifier
                    .zIndex(1f) // Garante que o ícone fique acima da caixa
                    .offset(y = if (isManualVisible) (-20).dp else 0.dp) // Move o ícone para cima quando a caixa aparece
            )

            IconButton(onClick = { isManualVisible = !isManualVisible }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "User Manual Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(28.dp)
                )
            }

            if (isManualVisible) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 42.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(18.dp)
                        .width(300.dp)
                ) {
                    Column {
                        Text(
                            text = "Manual de uso \n",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "1.Camera",
                            fontSize = 20.sp,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Para utilizar a câmera, será possível clicar no botão\n"+
                                    "para capturar uma foto.Após tirar a foto,você poderá \n" +
                                    "selecionar a área desejada para identificar a cor.\n" +
                                    "Em seguida, será direcionado para um menu rolável, \n"+
                                    "que exibirá detalhes sobre a cor escolhida.\n",
                            fontSize = 12.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "2.Barra Intuitiva",
                            fontSize = 20.sp,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Para usar a barra intuitiva será possivel arrasta-la.\n" +
                                    "E assim mostrando as seguintes informações.\n" +
                                    "Acesso às especificações detalhadas da cor escolhida.\n" +
                                    "Essas informações incluem os valores HEX, RGB e RYB,\n" +
                                    "Color Match,Color Temperature,Color Terminology.\n" +
                                    "Além disso, será exibida uma descrição sobre a cor.\n" +
                                    "Você também terá a opção de salvar a cor junto com\n" +
                                    " sua paleta personalizada de cores.\n",

                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "3.Barra de Paletas",
                            fontSize = 20.sp,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Parra usar-la você terar que arrasta-la para cima.\n" +
                                    "A barra de paleta de cores permitirá acessar facilmente,\n" +
                                    "suas paletas salvas além de um histórico  das cores\n" +
                                    "utilizadas anteriormente.\n" +
                                    "Isso facilita a organização e o rápido resgate de combinações e cores já trabalhadas.\n",

                            fontSize = 12.sp,
                            color = Color.Black
                        )



                        }


                    }
                }
            }
        }
        Spacer(Modifier.height(screenHeight * 0.02f))
        //
    }





@Preview
@Composable
fun UserDetailsPreview(){
    UserDetails()
}