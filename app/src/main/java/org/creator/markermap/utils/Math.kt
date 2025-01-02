package org.creator.markermap.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

fun Float.min(other: Float): Float = kotlin.math.min(this, other)

fun Float.max(other: Float): Float = kotlin.math.max(this, other)

//fun Double.asRadians(): Double = this * PI / 180.0

//fun Double.asDegrees(): Double = this * 180.0 / PI

fun Offset.transform(angle: Float, distance: Float): Offset =
    Offset(
        x + distance * cos(angle),
        y + distance * sin(angle)
    )