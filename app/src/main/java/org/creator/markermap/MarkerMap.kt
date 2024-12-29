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
import org.creator.markermap.model.MarkerMapModel

data class MarkerMapRenderingState (
    val circles: Int,
    var offset: Offset = Offset.Zero,
    var radius: Float = 100f,
    val meshRenderingSettings: MapMeshRenderingSettings = MapMeshRenderingSettings()

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

@Composable
fun MarkerMap(mapModel: MarkerMapModel) {
    var renderingState by remember { mutableStateOf(MarkerMapRenderingState(mapModel.circles)) }

    Box(
        Modifier
        .pointerInput(Unit) {
            detectTransformGestures(panZoomLock = true) { _, pan, zoom, _ ->
                renderingState = renderingState.copy(
                    offset = renderingState.offset + pan,
                    radius = renderingState.radius * zoom
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
            circles = mapModel.circles,
            rayCount = mapModel.rayCount,
            fieldOfView = mapModel.fieldOfView,
            offset = renderingState.correctedOffset(),
            radius = renderingState.correctedRadius(),
            rendererSettings = renderingState.meshRenderingSettings
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MarkerMapPreview() {
    MarkerMap(MarkerMapModel())
}
