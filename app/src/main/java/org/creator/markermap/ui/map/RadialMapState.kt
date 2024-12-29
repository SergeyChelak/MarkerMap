package org.creator.markermap.ui.map

import androidx.compose.ui.geometry.Offset
import org.creator.markermap.max
import org.creator.markermap.min


data class RadialMapState(
    val circles: Int,
    var offset: Offset = Offset.Zero,
    var radius: Float = 100f,
    var anchor: Offset = Offset.Zero,
    val meshSettings: RadialMeshSettings = RadialMeshSettings()

) {
    fun boundedOffset(): Offset {
        val len = radius * (circles - 3)
        val y = offset.y.min(len).max(0f)
        val x = offset.x.min(len).max(-len)
        return Offset(x, y)
    }

    fun boundedRadius(): Float =
        radius.max(50f).min(200f)
}
