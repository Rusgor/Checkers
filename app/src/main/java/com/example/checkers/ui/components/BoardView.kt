package com.example.checkers.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.checkers.board.Board
import com.example.checkers.model.Move
import com.example.checkers.model.Position

/**
 * Дошка 8×8 з усіма клітинками.
 */
@Composable
fun BoardView(
    board: Board,
    selectedPosition: Position?,
    possibleMoves: List<Move>,
    lastMove: Move?,
    onCellClick: (Position) -> Unit,
    modifier: Modifier = Modifier
) {
    val moveTargets = possibleMoves.map { it.to }.toSet()
    val captureTargets = possibleMoves.filter { it.isCapture }.map { it.to }.toSet()

    BoxWithConstraints(
        modifier = modifier.aspectRatio(1f)
    ) {
        val cellSize = maxWidth / 8

        Column {
            for (row in 0..7) {
                Row {
                    for (col in 0..7) {
                        val pos = Position(row, col)
                        CellView(
                            position = pos,
                            piece = board[pos],
                            cellSize = cellSize,
                            isSelected = pos == selectedPosition,
                            isMoveHint = pos in moveTargets && pos !in captureTargets,
                            isCaptureHint = pos in captureTargets,
                            isLastMove = lastMove?.let {
                                it.from == pos || it.to == pos
                            } == true,
                            onClick = { onCellClick(pos) }
                        )
                    }
                }
            }
        }
    }
}

