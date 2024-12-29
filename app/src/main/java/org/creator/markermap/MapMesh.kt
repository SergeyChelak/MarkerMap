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

@Preview(showBackground = true)
@Composable
fun MapMesh(
    circles: Int = 31,
    rays: Int = 13,
    fov: Double = PI / 2,
    radius: Float = 80f,
    offset: Offset = Offset(x = 0f, y = 100f)
) {
    val rayLength = radius * (circles - 1)
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(x = this.size.width / 2, y = this.size.height) + offset
        repeat(circles) { step ->
            drawCircle(
                center = center,
                color = Color.Gray,
                radius = radius * step,
                style = Stroke(width = strokeWidth(step))
            )
        }
        val angles = angles(fov, rays)
        repeat(rays) { ray ->
            val angle = angles[ray]
            drawLine(
                color = Color.Gray,
                start = center,
                end = center.rotate(angle, rayLength),
                strokeWidth = 5f
            )
        }
    }
}

fun strokeWidth(step: Int): Float = if (step % 3 == 0) 10f else 5f

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