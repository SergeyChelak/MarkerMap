package org.creator.markermap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import org.creator.markermap.data.MapDatabase
import org.creator.markermap.model.MarkerMap
import org.creator.markermap.ui.list.MapList
import org.creator.markermap.ui.map.RadialMap

enum class Navigation {
    MapList,
    MapView
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val database: MapDatabase = Room.databaseBuilder(
            navController.context.applicationContext,
            MapDatabase::class.java, "MapDatabase.db"
        ).build()

    NavHost(
        navController = navController,
        startDestination = Navigation.MapView.name
    ) {
        composable(route = Navigation.MapList.name) {
            MapList()
        }
        composable(route = Navigation.MapView.name) {
            RadialMap(map = MarkerMap())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkerMapAppPreview() {
    NavGraph()
}