package com.example.checkers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.checkers.model.PieceColor
import com.example.checkers.ui.theme.CheckersColors

/**
 * Інформаційна панель: чий хід, лічильник, кнопки.
 */
@Composable
fun InfoPanel(
    currentColor: PieceColor,
    moveCount: Int,
    winner: PieceColor?,
    isChainActive: Boolean,
    canUndo: Boolean,
    onUndo: () -> Unit,
    onNewGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CheckersColors.Panel)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Верхній рядок: назва + лічильник
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Шашки",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = CheckersColors.TextPrimary
            )
            Spacer(Modifier.weight(1f))
            Text(
                "Ходів: $moveCount",
                fontSize = 14.sp,
                color = CheckersColors.TextSecondary
            )
        }

        // Середній рядок: чий хід + переможець
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val dotColor = if (currentColor == PieceColor.WHITE)
                CheckersColors.WhitePiece else CheckersColors.BlackPiece

            Box(
                Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Text(
                text = if (currentColor == PieceColor.WHITE) "Білі" else "Чорні",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CheckersColors.TextPrimary
            )

            if (isChainActive) {
                Text(
                    "• Продовжте бій!",
                    fontSize = 13.sp,
                    color = CheckersColors.CaptureHint,
                    fontWeight = FontWeight.Medium
                )
            }

            winner?.let {
                Spacer(Modifier.weight(1f))
                Text(
                    "🏆 " + if (it == PieceColor.WHITE) "Білі" else "Чорні",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = CheckersColors.Crown
                )
            }
        }

        // Нижній рядок: кнопки
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onUndo,
                enabled = canUndo,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CheckersColors.ButtonSecondary,
                    disabledContainerColor = CheckersColors.ButtonDisabled
                )
            ) {
                Text("↶ Скасувати", fontSize = 13.sp)
            }
            Button(
                onClick = onNewGame,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CheckersColors.ButtonPrimary
                )
            ) {
                Text("⟳ Нова гра", fontSize = 13.sp)
            }
        }
    }
}

