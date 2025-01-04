package org.creator.markermap.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MapDao {
    @Query("SELECT * FROM map")
    fun getAll(): List<Map>
}