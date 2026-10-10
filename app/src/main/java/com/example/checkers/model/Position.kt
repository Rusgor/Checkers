package com.example.checkers.model

data class Position(val row: Int, val col: Int) {

    /** Ігрові (темні) клітинки — де сума row + col непарна */
    val isPlayable: Boolean get() = (row + col) % 2 == 1

    fun isInside(): Boolean = row in 0..7 && col in 0..7

    fun shift(dRow: Int, dCol: Int): Position =
        Position(row + dRow, col + dCol)

    override fun toString(): String = "($row,$col)"
}

