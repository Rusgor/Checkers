package com.example.checkers

import com.example.checkers.logic.GameEngine
import com.example.checkers.model.PieceColor
import com.example.checkers.model.PieceType
import com.example.checkers.model.Position
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

/**
 * Тести для ігрового рушія.
 */
class GameEngineTest {

    @Test
    fun `на початку хід у білих`() {
        val game = GameEngine()
        assertEquals(PieceColor.WHITE, game.currentColor)
    }

    @Test
    fun `на початку немає переможця`() {
        val game = GameEngine()
        assertNull(game.winner)
    }

    @Test
    fun `після коректного ходу хід переходить чорним`() {
        val game = GameEngine()
        val move = game.availableMoves().first()

        val ok = game.makeMove(move)
        assertTrue("Хід має бути виконаний", ok)
        assertEquals(PieceColor.BLACK, game.currentColor)
    }

    @Test
    fun `некоректний хід відхиляється`() {
        val game = GameEngine()
        val invalid = game.availableMoves().first().copy(to = Position(0, 0))

        val ok = game.makeMove(invalid)
        assertFalse("Некоректний хід не повинен виконуватись", ok)
    }

    @Test
    fun `undo повертає попередній стан`() {
        val game = GameEngine()
        val beforeMove = game.availableMoves().size
        val move = game.availableMoves().first()

        game.makeMove(move)
        assertEquals(PieceColor.BLACK, game.currentColor)

        val undone = game.undo()
        assertTrue("Undo має спрацювати", undone)
        assertEquals(PieceColor.WHITE, game.currentColor)
        assertEquals(beforeMove, game.availableMoves().size)
    }

    @Test
    fun `undo без ходів повертає false`() {
        val game = GameEngine()
        assertFalse(game.undo())
    }

    @Test
    fun `на старті всі шашки прості (не дамки)`() {
        val game = GameEngine()
        val white = game.board[Position(7, 0)]
        assertNotNull(white)
        assertEquals(PieceType.MAN, white!!.type)
    }
}

