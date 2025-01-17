package org.creator.markermap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "map"
)
data class Map(
    @PrimaryKey val uid: Int
)
