package com.prisma.prismavi.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ColorResponse(
    @SerializedName("Color") val color: ColorModel
)

@Parcelize
data class ColorModel(
    val name: String,
    val hexCode: String,
    val rgbCode: String,
    val rybPercentages: RybPercentages,
    val colorTemperature: String,
    val colorDescription: String,
    val twoHexOfColorsThatMatch: List<String>,
    val colorTerminology: String
) : Parcelable

@Parcelize
data class RybPercentages(
    val r: String,
    val y: String,
    val b: String
) : Parcelable