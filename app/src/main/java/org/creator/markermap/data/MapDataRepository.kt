package org.creator.markermap.data

interface MapDataRepository {
    suspend fun fetchAllMaps(): List<Map>
}

class LocalMapDataRepository(
    private val database: MapDatabase
): MapDataRepository {
    override suspend fun fetchAllMaps(): List<Map> {
        return database.getMapDao().getAll()
    }
}