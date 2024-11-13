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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserDetails(){

    val username: String = "Usuário!"

    val colorList = listOf("#000000","#9AEAC5","#007CC0")

    var palleteList = listOf("Paleta #1","Paleta #2","Paleta #3")

    val onAddPallete: () -> Unit = {
        palleteList += "Pallete${palleteList.size - 1}"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ){
            Text(
                text = "Seja Bem Vindo, $username",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }

        Spacer(Modifier.height(30.dp))

        Row(){
            Column {
                Text(
                    text = "Paletas",
                    fontSize = 16.sp,
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
                ){
                    Column {
                        for (pallete in palleteList)
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(Color.Gray.copy(alpha = 0.3f))
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = pallete,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600

                                )
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                            ){}
                        }
                        Button(
                            onClick = onAddPallete,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color.Green.copy(alpha = 0.09f))
                            ,
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Transparent)

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
        Row {
            Column {
                Text(
                    text = "Histórico de Cores",
                    fontSize = 16.sp,
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
                        verticalArrangement = Arrangement.spacedBy(8.dp) // espaçamento entre itens
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
                                        .size(35.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(parseColor(color))
                                )

                                Text(
                                    text = color,
                                    fontSize = 14.sp,
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