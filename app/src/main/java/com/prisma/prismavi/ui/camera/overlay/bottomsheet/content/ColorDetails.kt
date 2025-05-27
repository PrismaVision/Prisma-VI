package com.prisma.prismavi.ui.camera.overlay.bottomsheet.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prisma.prismavi.api.model.ColorModel
import com.prisma.prismavi.viewmodel.color.ColorViewModel

fun parseColor(colorString: String): Color {
    val colorLong = colorString.removePrefix("#").toLong(16)
    return Color(colorLong or 0xFF000000)
}

@Composable
fun Tittle_(text: String, screenWidth: Dp){
    Text(
        text = text,
        fontSize = (screenWidth * 0.045f).value.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Spacer_(){
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun ColorBox_(hex: String){
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(parseColor(hex))
    )
}

@Composable
fun ColorRow_(hex: String){
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
                .background(parseColor(hex))
        )
        Text(
            text = hex,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun ColorRow_(hex: String, name: String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.Gray.copy(alpha = 0.3f))
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(parseColor(hex))
        )
        Text(
            text = name,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
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
                Tittle_(color.name, screenWidth)
                Spacer_()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "HEX: ${color.hexCode}", fontSize = 14.sp)
                    color.rgb?.let {
                        Text(text = "RGB: ${it.red}, ${it.green}, ${it.blue}", fontSize = 14.sp)
                    } ?: Text("RGB: não disponível", fontSize = 14.sp)
                    Text(text = "HSL: ${color.hsl}", fontSize = 14.sp)
                    Text(text = "Luminância: ${"%.3f".format(color.luminance)}", fontSize = 14.sp)
                }
            }
        }

        //Psicologia das cores
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Tittle_("Psicologia das cores", screenWidth)
            Spacer_()
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
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //Sugestao de paleta
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp)
            ){
                Tittle_("Sugestão de paleta", screenWidth)
                Spacer_()
                Column{
                    color.colorPalette.forEach { item ->
                        ColorRow_(item.hex, item.name)
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)){
                Tittle_("Acessivel no branco", screenWidth)
                Spacer_()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ){
                    Text(
                        text = "true",
                        fontSize = (screenWidth * 0.045f).value.sp,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Tittle_("Acessivel ao preto", screenWidth)
                Spacer_()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ){
                    Text(
                        text = "false",
                        fontSize = (screenWidth * 0.045f).value.sp,
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Tittle_("Cor complementar - ${color.complementaryColor}", screenWidth)
            Spacer_()
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(parseColor(color.complementaryColor))
                    .padding(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Column(modifier = Modifier.weight(1f)) {
                Tittle_("Variações da cor", screenWidth)
                Spacer_()
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
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
            }

            Column(modifier = Modifier.weight(1f)) {
                Tittle_("Categoria da cor", screenWidth)
                Spacer_()
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ){
                    Text(
                        modifier = Modifier.padding(0.dp, 10.dp),
                        text = color.colorCategory,
                        fontSize = (screenWidth * 0.04f).value.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }


        }


        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Tittle_("Sugestões de Uso", screenWidth)
            Spacer_()
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
        }


        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Tittle_("Descrição", screenWidth)
            Spacer_()
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
}


@Composable
fun ColorDetailsContent(color: ColorModel){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val sheetHeight = screenHeight * 0.80f

    Column(
        modifier = Modifier
            .heightIn(0.dp, 2000.dp)
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
                Tittle_(color.name, screenWidth)
                Spacer_()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "HEX: ${color.hexCode}", fontSize = 14.sp)
                    color.rgb?.let {
                        Text(text = "RGB: ${it.red}, ${it.green}, ${it.blue}", fontSize = 14.sp)
                    } ?: Text("RGB: não disponível", fontSize = 14.sp)
                    Text(text = "HSL: ${color.hsl}", fontSize = 14.sp)
                    Text(text = "Luminância: ${"%.3f".format(color.luminance)}", fontSize = 14.sp)
                }
            }
        }

        //Psicologia das cores
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Tittle_("Psicologia das cores", screenWidth)
            Spacer_()
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
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row( modifier = Modifier.fillMaxWidth(),
             horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
            //Sugestao de paleta
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp)
            ){
                Tittle_("Sugestão de paleta", screenWidth)
                Spacer_()
                Column{
                    color.colorPalette.forEach { item ->
                        ColorRow_(item.hex, item.name)
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)){
                Tittle_("Acessivel no branco", screenWidth)
                Spacer_()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ){
                    Text(
                        text = "true",
                        fontSize = (screenWidth * 0.045f).value.sp,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Tittle_("Acessivel ao preto", screenWidth)
                Spacer_()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ){
                    Text(
                        text = "false",
                        fontSize = (screenWidth * 0.045f).value.sp,
                    )
                }
        }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Tittle_("Cor complementar - ${color.complementaryColor}", screenWidth)
            Spacer_()
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(parseColor(color.complementaryColor))
                    .padding(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Column(modifier = Modifier.weight(1f)) {
                Tittle_("Variações da cor", screenWidth)
                Spacer_()
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
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
            }

            Column(modifier = Modifier.weight(1f)) {
                Tittle_("Categoria da cor", screenWidth)
                Spacer_()
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .padding(12.dp)
                ){
                    Text(
                        modifier = Modifier.padding(0.dp, 10.dp),
                        text = color.colorCategory,
                        fontSize = (screenWidth * 0.04f).value.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }


        }


        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Tittle_("Sugestões de Uso", screenWidth)
            Spacer_()
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
        }


        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Tittle_("Descrição", screenWidth)
            Spacer_()
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
}

//@Preview(showBackground = true)
//@Composable
//fun ColorDetailsPreview() {
//    val mockColor = ColorModel(
//        name = "Azul Prisma",
//        hexCode = "#1E90FF",
//        rgb = RGB(30, 144, 255),
//        hsl = "210°, 100%, 56%",
//        luminance = 0.72,
//        psychologyTags = listOf("Calma", "Confiança", "Segurança","Estabilidade"),
//        colorPalette = listOf(
//            ColorItem("Azul Claro", "#ADD8E6"),
//            ColorItem("Azul Escuro", "#00008B")
//        ),
//        complementaryColor = "#008C22",
//        hexVariations = listOf("#1C86EE", "#1874CD", "#104E8B"),
//        colorCategory = "Secundária (Verde)",
//        designUsageSuggestions = listOf("Header de app", "Botões de ação"),
//        description = "Uma cor que transmite calma e segurança, usada em designs modernos.",
//        accessibleOnBlack = true,
//        accessibleOnWhite = false,
//
//    )
//    ColorDetailsContent(mockColor)
//}
