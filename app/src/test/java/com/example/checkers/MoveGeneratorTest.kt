package com.example.checkers

import com.example.checkers.board.Board
import com.example.checkers.logic.MoveGenerator
import com.example.checkers.model.PieceColor
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

/**
 * Тести для генератора ходів.
 * Покривають базові правила російських шашок.
 */
class MoveGeneratorTest {

    @Test
    fun `початкова дошка має 12 білих фігур`() {
        val board = Board()
        val white = board.allPieces(PieceColor.WHITE)
        assertEquals(12, white.size)
    }

    @Test
    fun `початкова дошка має 12 чорних фігур`() {
        val board = Board()
        val black = board.allPieces(PieceColor.BLACK)
        assertEquals(12, black.size)
    }

    @Test
    fun `на початку у білих рівно 7 доступних ходів`() {
        val board = Board()
        val moves = MoveGenerator.getAllMoves(board, PieceColor.WHITE)
        assertEquals(7, moves.size)
    }

    @Test
    fun `на початку у чорних рівно 7 доступних ходів`() {
        val board = Board()
        val moves = MoveGenerator.getAllMoves(board, PieceColor.BLACK)
        assertEquals(7, moves.size)
    }

    @Test
    fun `на початку немає жодного взяття`() {
        val board = Board()
        val captures = MoveGenerator.getAllCaptures(board, PieceColor.WHITE)
        assertTrue(captures.isEmpty())
    }

    @Test
    fun `білі ходять вгору (менший номер рядка)`() {
        val board = Board()
        val moves = MoveGenerator.getAllMoves(board, PieceColor.WHITE)
        assertTrue(moves.all { it.to.row < it.from.row })
    }

    @Test
    fun `чорні ходять вниз (більший номер рядка)`() {
        val board = Board()
        val moves = MoveGenerator.getAllMoves(board, PieceColor.BLACK)
        assertTrue(moves.all { it.to.row > it.from.row })
    }
}

