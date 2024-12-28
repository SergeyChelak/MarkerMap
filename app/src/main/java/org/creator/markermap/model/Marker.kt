package org.creator.markermap.model

typealias MapId = UInt;

data class Map(val id: MapId) {
    var title: String = ""
    var coordinate: GpsCoordinate? = null
    var nodes: Array<Marker> = emptyArray()
}

data class GpsCoordinate(
    val latitude: Double,
    val longitude: Double,
)

data class Marker(val angle: Int) {
    var mark: String = ""
    var distance: Double = 0.0
    var depth: Double = 0.0
    var type: MarkerType = MarkerType.NONE;
}

enum class MarkerType {
    NONE,
    GROUND
    // add more
}