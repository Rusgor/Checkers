package com.example.checkers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.checkers.model.Piece
import com.example.checkers.model.Position
import com.example.checkers.ui.theme.CheckersColors

/**
 * Одна клітинка дошки: фон + підсвічування + фігура.
 */
@Composable
fun CellView(
    position: Position,
    piece: Piece?,
    cellSize: Dp,
    isSelected: Boolean,
    isMoveHint: Boolean,
    isCaptureHint: Boolean,
    isLastMove: Boolean,
    onClick: () -> Unit
) {
    val baseColor = if (position.isPlayable)
        CheckersColors.DarkCell
    else
        CheckersColors.LightCell

    val highlightColor: Color? = when {
        isSelected -> CheckersColors.SelectedCell
        isCaptureHint -> CheckersColors.CaptureHint
        isMoveHint -> CheckersColors.MoveHint
        isLastMove -> CheckersColors.LastMove
        else -> null
    }

    Box(
        modifier = Modifier
            .size(cellSize)
            .background(baseColor)
            .clickable(enabled = position.isPlayable) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Шар підсвічування
        if (highlightColor != null) {
            Box(Modifier.size(cellSize).background(highlightColor))
        }

        // Фігура
        piece?.let { PieceView(piece = it, cellSize = cellSize) }

        // Крапка-підказка для порожньої клітинки
        if (isMoveHint && piece == null) {
            Box(
                Modifier
                    .size(cellSize * 0.28f)
                    .clip(CircleShape)
                    .background(CheckersColors.MoveHint.copy(alpha = 0.9f))
            )
        }
    }
}

