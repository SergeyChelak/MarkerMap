package org.creator.markermap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import org.creator.markermap.ui.theme.MarkerMapTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarkerMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MarkerMap()
                }
            }
        }
    }
}

data class MarkerMapState (
    var isActive: Boolean = false,
    var offset: Offset = Offset(x = 0f, y = 0f),
    var radius: Float = 100f,
    val circles: Int = 31
) {
    fun correctedOffset(): Offset {
        val len = radius * (circles - 1)
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

    Box(Modifier
        .pointerInput(Unit) {
            detectTransformGestures(panZoomLock = true) { _, pan, zoom, _ ->
                mapState = mapState.copy(
                    offset = mapState.offset + pan,
                    radius = mapState.radius * zoom
                )
            }
        }
    ) {
        MapMesh(
            circles = mapState.circles,
            offset = mapState.correctedOffset(),
            radius = mapState.correctedRadius()
        )
    }
}