package com.example.checkers.logic

import com.example.checkers.board.Board
import com.example.checkers.model.Move
import com.example.checkers.model.PieceColor
import com.example.checkers.model.Position

object MoveGenerator {

    private val DIRS_MAN_FORWARD = mapOf(
        PieceColor.WHITE to listOf(-1 to -1, -1 to 1),
        PieceColor.BLACK to listOf(1 to -1, 1 to 1)
    )

    private val DIRS_ALL = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)

    fun getAllMoves(board: Board, color: PieceColor): List<Move> {
        val captures = getAllCaptures(board, color)
        if (captures.isNotEmpty()) return captures

        val simple = mutableListOf<Move>()
        for (pos in board.allPieces(color)) {
            val piece = board[pos] ?: continue
            if (piece.isKing) {
                simple += generateKingSimpleMoves(board, pos)
            } else {
                simple += generateManSimpleMoves(board, pos, color)
            }
        }
        return simple
    }

    private fun generateManSimpleMoves(
        board: Board, from: Position, color: PieceColor
    ): List<Move> {
        val dirs = DIRS_MAN_FORWARD.getValue(color)
        return dirs.mapNotNull { (dr, dc) ->
            val to = from.shift(dr, dc)
            if (to.isInside() && to.isPlayable && board.isEmpty(to))
                Move(from, to)
            else null
        }
    }

    private fun generateKingSimpleMoves(
        board: Board, from: Position
    ): List<Move> {
        val result = mutableListOf<Move>()
        for ((dr, dc) in DIRS_ALL) {
            var cur = from.shift(dr, dc)
            while (cur.isInside() && cur.isPlayable && board.isEmpty(cur)) {
                result += Move(from, cur)
                cur = cur.shift(dr, dc)
            }
        }
        return result
    }

    fun getAllCaptures(board: Board, color: PieceColor): List<Move> {
        val result = mutableListOf<Move>()
        for (pos in board.allPieces(color)) {
            val piece = board[pos] ?: continue
            if (piece.isKing) {
                result += generateKingCaptures(board, pos, color)
            } else {
                result += generateManCaptures(board, pos, color)
            }
        }
        return result
    }

    private fun generateManCaptures(
        board: Board, from: Position, color: PieceColor
    ): List<Move> {
        val result = mutableListOf<Move>()
        for ((dr, dc) in DIRS_ALL) {
            val mid = from.shift(dr, dc)
            val to = from.shift(dr * 2, dc * 2)
            if (!to.isInside() || !to.isPlayable) continue
            if (board.isEnemy(mid, color) && board.isEmpty(to)) {
                result += Move(from, to, listOf(mid))
            }
        }
        return result
    }

    fun generateKingCaptures(
        board: Board, from: Position, color: PieceColor
    ): List<Move> {
        val result = mutableListOf<Move>()
        for ((dr, dc) in DIRS_ALL) {
            var cur = from.shift(dr, dc)
            while (cur.isInside() && cur.isPlayable && board.isEmpty(cur)) {
                cur = cur.shift(dr, dc)
            }
            if (!cur.isInside() || !cur.isPlayable) continue
            if (!board.isEnemy(cur, color)) continue

            val enemyPos = cur
            var landing = cur.shift(dr, dc)
            while (landing.isInside() && landing.isPlayable && board.isEmpty(landing)) {
                result += Move(from, landing, listOf(enemyPos))
                landing = landing.shift(dr, dc)
            }
        }
        return result
    }
}
