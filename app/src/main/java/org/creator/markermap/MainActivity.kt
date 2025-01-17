package org.creator.markermap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import org.creator.markermap.data.LocalMapDataRepository
import org.creator.markermap.data.MapDataRepository
import org.creator.markermap.data.MapDatabase
import org.creator.markermap.ui.theme.MarkerMapTheme

class MainActivity : ComponentActivity() {
    private val repository: MapDataRepository by lazy {
        val db = Room.databaseBuilder(
            this,
            MapDatabase::class.java, "MapDatabase.db"
        ).build()
        LocalMapDataRepository(db)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarkerMapTheme {
                NavGraph()
            }
        }
    }
}