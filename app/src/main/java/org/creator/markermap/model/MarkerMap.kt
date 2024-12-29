package org.creator.markermap.model

import java.util.UUID
import kotlin.math.PI

data class MarkerMap(
    val id: UUID = UUID.randomUUID(),
    val circles: Int = 33,
    val rayCount: Int = 11,
    val fieldOfView: Double = PI * 0.5,
    var title: String = ""
)
//data class GpsCoordinate(
//    val latitude: Double,
//    val longitude: Double,
//)
//
//data class Marker(val angle: Int) {
//    var mark: String = ""
//    var distance: Double = 0.0
//    var depth: Double = 0.0
//    var type: MarkerType = MarkerType.NONE;
//}
//
//enum class MarkerType {
//    NONE,
//    GROUND
//    // add more
//}