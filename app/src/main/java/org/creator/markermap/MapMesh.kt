package org.creator.markermap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class MapMeshRenderingSettings(
    val lineColor: Color = Color.Gray,
    val normalStrokeWidth: Float = 5f,
    val boldStrokeWidth: Float = 10f
) {
    fun circleStrokeWidth(circle: Int): Float =
        if (circle % 3 == 0) boldStrokeWidth else normalStrokeWidth
}

@Preview(showBackground = true)
@Composable
fun MapMesh(
    circles: Int = 31,
    rays: Int = 13,
    fov: Double = PI / 2,
    radius: Float = 80f,
    offset: Offset = Offset(x = 0f, y = 100f),
    rendererSettings: MapMeshRenderingSettings = MapMeshRenderingSettings()
) {
    val rayLength = radius * (circles - 1)
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(x = this.size.width / 2, y = this.size.height) + offset
        repeat(circles) { step ->
            drawCircle(
                center = center,
                color = rendererSettings.lineColor,
                radius = radius * step,
                style = Stroke(width = rendererSettings.circleStrokeWidth(step))
            )
        }
        val angles = angles(fov, rays)
        repeat(rays) { ray ->
            val angle = angles[ray]
            drawLine(
                color = rendererSettings.lineColor,
                start = center,
                end = center.rotate(angle, rayLength),
                strokeWidth = rendererSettings.normalStrokeWidth
            )
        }
    }
}

fun angles(fov: Double, rays: Int): List<Float> {
    if (rays < 1) {
        return emptyList()
    }
    val stepSize = fov / (rays - 1)
    val start = -0.5 * fov;
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

fun Offset.rotate(angle: Float, length: Float): Offset =
    Offset(x = this.x + length * cos(angle), y = y + length * sin(angle))