package com.example.checkers.board

import com.example.checkers.model.Piece
import com.example.checkers.model.PieceColor
import com.example.checkers.model.Position

class Board {

    private val cells: Array<Array<Piece?>> =
        Array(8) { arrayOfNulls(8) }

    init {
        setupInitialPosition()
    }

    private fun setupInitialPosition() {
        for (row in 0..7) {
            for (col in 0..7) {
                val pos = Position(row, col)
                if (!pos.isPlayable) continue

                when (row) {
                    in 0..2 -> cells[row][col] = Piece(PieceColor.BLACK)
                    in 5..7 -> cells[row][col] = Piece(PieceColor.WHITE)
                }
            }
        }
    }

    operator fun get(pos: Position): Piece? =
        if (pos.isInside()) cells[pos.row][pos.col] else null

    operator fun set(pos: Position, piece: Piece?) {
        if (pos.isInside()) cells[pos.row][pos.col] = piece
    }

    fun isEmpty(pos: Position): Boolean = get(pos) == null

    fun isEnemy(pos: Position, color: PieceColor): Boolean =
        get(pos)?.color == color.opposite()

    fun isOwn(pos: Position, color: PieceColor): Boolean =
        get(pos)?.color == color

    fun copy(): Board {
        val newBoard = Board()
        for (row in 0..7) {
            for (col in 0..7) {
                newBoard.cells[row][col] = this.cells[row][col]
            }
        }
        return newBoard
    }

    fun allPieces(color: PieceColor): List<Position> = buildList {
        for (row in 0..7) for (col in 0..7) {
            val pos = Position(row, col)
            if (get(pos)?.color == color) add(pos)
        }
    }
}

