package org.creator.markermap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.creator.markermap.model.MarkerMapModel
import org.creator.markermap.ui.theme.MarkerMapTheme

class MainActivity : ComponentActivity() {
    private val map: MarkerMapModel = MarkerMapModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarkerMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MarkerMap(map)
                }
            }
        }
    }
}