package org.creator.markermap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Map(
    @PrimaryKey val uid: Int
)
