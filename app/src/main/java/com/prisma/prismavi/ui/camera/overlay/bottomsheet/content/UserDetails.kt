package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserDetails(){

    val colorList = listOf("#000000","#000001","#000011")

    var palleteList = listOf("Pallete1","Pallete2","Pallete3")

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
                text = "{Tittle variable}",
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
        Row(){
            Column {
                Text(
                    text = "{Tittle variable}",
                    fontSize = 16.sp,
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
                                )
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                            ){}
                        }
                        Button(
                            onClick = onAddPallete,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color.Green.copy(alpha = 0.2f))
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
        Row(){
            Column {
                Text(
                    text = "{Tittle variable}",
                    fontSize = 16.sp,
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
                        for (color in colorList)
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(Color.Gray.copy(alpha = 0.3f))
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(45.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(parseColor(color))
                                            .padding(10.dp)
                                    )

                                    Text(
                                        text = color,
                                        fontSize = 14.sp,
                                        modifier = Modifier
                                            .padding(start = 4.dp)
                                    )
                                }
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                            ){}
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