package org.creator.markermap.model

data class CellPosition(
    val row: Int,
    val col: Int
) {
    companion object {
        val zero: CellPosition = CellPosition(row = 0, col = 0)
    }
}