package com.prisma.prismavi.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//Corpo principal da resposta da API.
data class ColorResponse(
    val color: ColorModel
)

//Mapeia os detalhes da cor.
data class ColorModel(
    val name: String,
    @SerializedName("hex") val hexCode: String?,
    @SerializedName("rgb") val rgb: RGB?,
    val hsl: String,
    val luminance: Double,
    val description: String,
    val psychologyTags: List<String>,
    val colorPalette: List<ColorItem>,
    val complementaryColor: String,
    val hexVariations: List<String>,
    val colorCategory: String,
    val designUsageSuggestions: List<String>,
    val accessibleOnWhite: Boolean,
    val accessibleOnBlack: Boolean
)

//Representa os três canais RGB de uma cor
data class RGB(
    val red: Int,
    val green: Int,
    val blue: Int
)
//Representa uma cor da paleta complementar/relacionada
data class ColorItem(
    val name: String,
    val hex: String
)
