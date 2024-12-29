package org.creator.markermap.ui.map

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.tooling.preview.Preview
import org.creator.markermap.model.CellPosition
import org.creator.markermap.model.MarkerMap
import kotlin.math.atan2

@Composable
fun RadialMap(
    map: MarkerMap
) {
    var renderingState by remember { mutableStateOf(RadialMapState(map.circles)) }

    Box(Modifier
        .onPlaced {
            val anchor = Offset(
                x = it.size.width.toFloat() / 2,
                y = it.size.height.toFloat()
            )
            renderingState = renderingState.copy(anchor = anchor)
        }
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
                offsetToPosition(offset, map, renderingState)?.let {
                    Log.d("[MarkerMap]", "Selected cell at $it")
                }
            })
        }
    ) {
        Text(map.title)
        RadialMesh(
            circles = map.circles,
            rayCount = map.rayCount,
            fieldOfView = map.fieldOfView,
            offset = renderingState.boundedOffset(),
            radius = renderingState.boundedRadius(),
            anchor = renderingState.anchor,
            rendererSettings = renderingState.meshSettings
        )
    }
}

fun offsetToPosition(
    offset: Offset,
    map: MarkerMap,
    renderingState: RadialMapState
): CellPosition? {
    val pos = offset - renderingState.boundedOffset();
    val normalized = pos - renderingState.anchor

    val distance = normalized.getDistance()
    val fRow = distance / renderingState.boundedRadius()
    if (fRow < 0f) {
        Log.d("[MarkerMap]", "point is out of field of view (NR)")
        return null
    }

    val angle = atan2(normalized.y, normalized.x).toDouble()
    val arc = map.fieldOfView / map.rayCount
    val fCol = -(0.5 * map.fieldOfView + angle) / arc
    if (fCol < 0f) {
        Log.d("[MarkerMap]", "point is out of field of view (NC)")
        return null
    }

    val row = fRow.toInt()
    val col = fCol.toInt()

    if (row >= map.circles || col >= map.rayCount) {
        Log.d("[MarkerMap]", "point is out of field of view (B R|C)")
        return null
    }
    return CellPosition(row, col)
}

@Preview(showBackground = true)
@Composable
fun MarkerMapPreview() {
    RadialMap(map = MarkerMap())
}
