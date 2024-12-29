package org.creator.markermap

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview

data class MarkerMapState (
    var isActive: Boolean = false,
    var offset: Offset = Offset.Zero,
    var radius: Float = 100f,
    val circles: Int = 31
) {
    fun correctedOffset(): Offset {
        val len = radius * (circles - 3)
        val y = offset.y.min(len).max(radius)
        val x = offset.x.min(len).max(-len)
        return Offset(x, y)
    }

    fun correctedRadius(): Float =
        radius.max(50f).min(200f)
}

@Preview(showBackground = true)
@Composable
fun MarkerMap() {
    var mapState by remember { mutableStateOf(MarkerMapState()) }

    Box(
        Modifier
        .pointerInput(Unit) {
            detectTransformGestures(panZoomLock = true) { _, pan, zoom, _ ->
                mapState = mapState.copy(
                    offset = mapState.offset + pan,
                    radius = mapState.radius * zoom
                )
            }
        }
        .pointerInput(Unit) {
            detectTapGestures(onTap = { offset ->
                Log.d("[MarkerMap]", "Tap at $offset")
            })
        }
    ) {
        MapMesh(
            circles = mapState.circles,
            offset = mapState.correctedOffset(),
            radius = mapState.correctedRadius()
        )
    }
}