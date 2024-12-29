package org.creator.markermap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import org.creator.markermap.ui.theme.MarkerMapTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarkerMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MapMesh()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapMesh(circles: Int = 28, rays: Int = 13, fov: Double = PI / 2, radius: Float = 100f) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(x = this.size.width / 2, y = this.size.height + 100f)
        repeat(circles) { step ->
            drawCircle(
                center = center,
                color = Color.Gray,
                radius = radius * step,
                style = Stroke(width = strokeWidth(step))
            )
        }
        val angles = angles(fov, rays)
        val len = radius * circles
        val x = center.x
        val y = center.y
        repeat(rays) { ray ->
            val angle = angles[ray]
            drawLine(
                color = Color.Gray,
                start = center,
                end = Offset(x = x + len * cos(angle), y = y + len * sin(angle)),
                strokeWidth = 5f
            )
        }
    }
}

fun strokeWidth(step: Int): Float = if (step % 3 == 0) 10f else 5f

fun angles(fov: Double, rays: Int): List<Float> {
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

//fun Double.asRadians(): Double = this * PI / 180.0

//fun Double.asDegrees(): Double = this * 180.0 / PI
