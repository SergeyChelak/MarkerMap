package org.creator.markermap.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Map::class], version = 1)
abstract class MapDatabase: RoomDatabase() {
    abstract fun getMapDao(): MapDao
}