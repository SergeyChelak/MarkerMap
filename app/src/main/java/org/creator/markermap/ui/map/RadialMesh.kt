package org.creator.markermap.ui.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun RadialMesh(
    circles: Int,
    rayCount: Int,
    fieldOfView: Double,
    radius: Float,
    anchor: Offset,
    offset: Offset,
    rendererSettings: RadialMeshSettings
) {
    val rayLength = radius * circles
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = anchor + offset
        repeat(circles) { step ->
            drawCircle(
                center = center,
                color = rendererSettings.lineColor,
                radius = radius * (step + 1),
                style = Stroke(width = rendererSettings.circleStrokeWidth(step))
            )
        }
        val angles = calculateRayAngles(fieldOfView, rayCount)
        repeat(rayCount) { ray ->
            val angle = angles[ray]
            drawLine(
                color = rendererSettings.lineColor,
                start = center,
                end = center.transform(angle, rayLength),
                strokeWidth = rendererSettings.normalStrokeWidth
            )
        }
    }
}

fun calculateRayAngles(fieldOfView: Double, rays: Int): List<Float> {
    if (rays < 1) {
        return emptyList()
    }
    val stepSize = fieldOfView / (rays - 1)
    val start = -0.5 * fieldOfView;
    return (0..rays)
        .map {
            start + it * stepSize
        }
        .map {
            -PI * 0.5 - it
        }
        .map {
            (2 * PI + it) % (2 * PI)
        }
        .map {
            it.toFloat()
        }
}

fun Offset.transform(angle: Float, distance: Float): Offset =
    Offset(
        x + distance * cos(angle),
        y + distance * sin(angle)
    )


@Preview(showBackground = true)
@Composable
fun MapMeshPreview() {
    RadialMesh(
        circles = 21,
        rayCount = 13,
        fieldOfView = PI / 2,
        radius = 80f,
        anchor = Offset(x = 520f, y = 2340f),
        offset = Offset(x = 0f, y = 0f),
        rendererSettings = RadialMeshSettings()
    )
}