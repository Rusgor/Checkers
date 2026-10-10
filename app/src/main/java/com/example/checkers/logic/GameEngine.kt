package com.example.checkers.logic

import com.example.checkers.board.Board
import com.example.checkers.model.Move
import com.example.checkers.model.PieceColor
import com.example.checkers.model.PieceType
import com.example.checkers.model.Position

class GameEngine {

    var board: Board = Board()
        private set

    var currentColor: PieceColor = PieceColor.WHITE
        private set

    var winner: PieceColor? = null
        private set

    var chainFrom: Position? = null
        private set

    private val history = ArrayDeque<Pair<Board, PieceColor>>()

    fun availableMoves(): List<Move> {
        chainFrom?.let { from ->
            val piece = board[from] ?: return emptyList()
            return if (piece.isKing)
                MoveGenerator.generateKingCaptures(board, from, piece.color)
            else
                MoveGenerator.getAllCaptures(board, piece.color)
                    .filter { it.from == from }
        }
        return MoveGenerator.getAllMoves(board, currentColor)
    }

    fun makeMove(move: Move): Boolean {
        val legal = availableMoves()
        if (legal.none { it.from == move.from && it.to == move.to }) return false

        history.addLast(board.copy() to currentColor)
        if (history.size > 100) history.removeFirst()

        val piece = board[move.from] ?: return false
        board[move.from] = null
        board[move.to] = piece

        for (cap in move.captured) board[cap] = null

        promoteIfNeeded(move.to)

        if (move.isCapture) {
            val continued = capturesFrom(move.to)
            if (continued.isNotEmpty()) {
                chainFrom = move.to
                return true
            }
        }

        endTurn()
        return true
    }

    private fun endTurn() {
        chainFrom = null
        currentColor = currentColor.opposite()
        checkGameOver()
    }

    fun undo(): Boolean {
        val last = history.removeLastOrNull() ?: return false
        board = last.first
        currentColor = last.second
        chainFrom = null
        winner = null
        return true
    }

    private fun promoteIfNeeded(pos: Position) {
        val piece = board[pos] ?: return
        if (piece.isKing) return
        val lastRow = if (piece.color == PieceColor.WHITE) 0 else 7
        if (pos.row == lastRow) {
            board[pos] = piece.copy(type = PieceType.KING)
        }
    }

    private fun capturesFrom(from: Position): List<Move> {
        val piece = board[from] ?: return emptyList()
        return if (piece.isKing)
            MoveGenerator.generateKingCaptures(board, from, piece.color)
        else
            MoveGenerator.getAllCaptures(board, piece.color)
                .filter { it.from == from }
    }

    private fun checkGameOver() {
        val moves = MoveGenerator.getAllMoves(board, currentColor)
        if (moves.isEmpty()) winner = currentColor.opposite()
    }
}
