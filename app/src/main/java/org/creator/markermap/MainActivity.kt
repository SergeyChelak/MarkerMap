package org.creator.markermap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.creator.markermap.model.MarkerMapModel
import org.creator.markermap.ui.theme.MarkerMapTheme

class MainActivity : ComponentActivity() {
    private val map: MarkerMapModel = MarkerMapModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarkerMapTheme {
                MarkerMap(map)
            }
        }
    }
}