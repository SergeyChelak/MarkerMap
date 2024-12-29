package org.creator.markermap.ui.map

import androidx.compose.ui.graphics.Color


data class RadialMeshSettings(
    val lineColor: Color = Color.Gray,
    val normalStrokeWidth: Float = 5f,
    val boldStrokeWidth: Float = 10f,
    val subDivision: Int = 4,
) {
    fun circleStrokeWidth(circle: Int): Float =
        if (circle % subDivision == 0) boldStrokeWidth else normalStrokeWidth
}