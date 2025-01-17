package org.creator.markermap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.creator.markermap.model.MarkerMap
import org.creator.markermap.ui.list.MapList
import org.creator.markermap.ui.map.RadialMap

enum class Route {
    MapList,
    MapView
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.MapView.name
    ) {
        composable(route = Route.MapList.name) {
            MapList()
        }
        composable(route = Route.MapView.name) {
            RadialMap(map = MarkerMap())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkerMapAppPreview() {
    NavGraph()
}