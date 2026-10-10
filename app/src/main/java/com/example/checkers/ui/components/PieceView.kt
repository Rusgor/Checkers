package com.example.checkers.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.example.checkers.model.Piece
import com.example.checkers.model.PieceColor
import com.example.checkers.ui.theme.CheckersColors

/**
 * Малює одну фігуру (просту шашку або дамку).
 */
@Composable
fun PieceView(
    piece: Piece,
    cellSize: Dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(cellSize)) {
        drawPiece(piece, size.minDimension)
    }
}

private fun DrawScope.drawPiece(piece: Piece, size: Float) {
    val radius = size * 0.36f
    val center = Offset(this.size.width / 2f, this.size.height / 2f)

    val bodyColor = if (piece.color == PieceColor.WHITE)
        CheckersColors.WhitePiece else CheckersColors.BlackPiece
    val borderColor = if (piece.color == PieceColor.WHITE)
        CheckersColors.WhitePieceBorder else CheckersColors.BlackPieceBorder

    // Тінь
    drawCircle(
        color = Color(0x55000000),
        radius = radius,
        center = center.copy(y = center.y + size * 0.04f)
    )
    // Тіло шашки
    drawCircle(color = bodyColor, radius = radius, center = center)
    // Обведення
    drawCircle(
        color = borderColor,
        radius = radius,
        center = center,
        style = Stroke(width = size * 0.03f)
    )
    // Внутрішній блик
    drawCircle(
        color = borderColor.copy(alpha = 0.35f),
        radius = radius * 0.65f,
        center = center,
        style = Stroke(width = size * 0.02f)
    )
    // Корона для дамки
    if (piece.isKing) {
        drawCrown(center, radius)
    }
}

private fun DrawScope.drawCrown(center: Offset, radius: Float) {
    val crownColor = CheckersColors.Crown
    val crownSize = radius * 0.9f

    val path = Path().apply {
        val top = center.y - crownSize * 0.55f
        val bottom = center.y + crownSize * 0.35f
        val left = center.x - crownSize * 0.7f
        val right = center.x + crownSize * 0.7f

        moveTo(left, bottom)
        lineTo(left, top)
        lineTo(center.x - crownSize * 0.3f, top + crownSize * 0.4f)
        lineTo(center.x, top - crownSize * 0.1f)
        lineTo(center.x + crownSize * 0.3f, top + crownSize * 0.4f)
        lineTo(right, top)
        lineTo(right, bottom)
        close()
    }
    drawPath(path, crownColor)
}

