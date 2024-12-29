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
import org.creator.markermap.model.CellPosition

data class MarkerMapRenderingState (
    val circles: Int,
    var offset: Offset = Offset.Zero,
    var radius: Float = 100f,
    val meshRenderingSettings: MapMeshRenderingSettings = MapMeshRenderingSettings()

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
                //Log.d("[MarkerMap]", "Tap at $offset")
                val anchor = Offset(x = this.size.width.toFloat() / 2, y = this.size.height.toFloat())
                offsetToPosition(offset, anchor, mapModel, renderingState)
            })
        }
    ) {
        MapMesh(
            circles = mapModel.circles,
            rayCount = mapModel.rayCount,
            fieldOfView = mapModel.fieldOfView,
            offset = renderingState.boundedOffset(),
            radius = renderingState.boundedRadius(),
            rendererSettings = renderingState.meshRenderingSettings
        )
    }
}

fun offsetToPosition(
    offset: Offset,
    anchor: Offset,
    map: MarkerMapModel,
    renderingState: MarkerMapRenderingState
): CellPosition? {
    val distance = (offset - anchor - renderingState.boundedOffset()).getDistance()
    val row = distance / renderingState.boundedRadius()
    Log.d("[MarkerMap]", "Row = $row")

    return null
}

@Preview(showBackground = true)
@Composable
fun MarkerMapPreview() {
    MarkerMap(MarkerMapModel())
}
