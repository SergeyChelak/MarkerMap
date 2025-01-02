package org.creator.markermap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.creator.markermap.model.MarkerMap
import org.creator.markermap.ui.list.MapList
import org.creator.markermap.ui.map.RadialMap

class MarkerMapAppViewModel : ViewModel() {

}

enum class Destination {
    MapList,
    MapView
}

@Composable
fun MarkerMapApp(viewModel: MarkerMapAppViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.MapView.name
    ) {
        composable(route = Destination.MapList.name) {
            MapList()
        }
        composable(route = Destination.MapView.name) {
            RadialMap(map = MarkerMap())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkerMapAppPreview() {
    MarkerMapApp(MarkerMapAppViewModel())
}