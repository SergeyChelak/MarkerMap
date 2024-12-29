package org.creator.markermap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.creator.markermap.model.MarkerMap
import org.creator.markermap.ui.map.RadialMap
import org.creator.markermap.ui.theme.MarkerMapTheme

class MainActivity : ComponentActivity() {
    private val map: MarkerMap = MarkerMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarkerMapTheme {
                RadialMap(map)
            }
        }
    }
}