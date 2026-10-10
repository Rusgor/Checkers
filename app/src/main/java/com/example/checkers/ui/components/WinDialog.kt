package com.example.checkers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.checkers.model.PieceColor
import com.example.checkers.ui.theme.CheckersColors

/**
 * Діалог перемоги з привітанням.
 */
@Composable
fun WinDialog(
    winner: PieceColor,
    moveCount: Int,
    onNewGame: () -> Unit,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            CheckersColors.Panel,
                            CheckersColors.Background
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Заголовок
                Text(
                    text = "🏆 Перемога!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = CheckersColors.Crown,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(4.dp))

                // Іконка переможця — кружечок відповідного кольору
                val winnerColor = if (winner == PieceColor.WHITE)
                    CheckersColors.WhitePiece else CheckersColors.BlackPiece
                val winnerBorder = if (winner == PieceColor.WHITE)
                    CheckersColors.WhitePieceBorder else CheckersColors.BlackPieceBorder

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(50))
                        .background(winnerColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👑",
                        fontSize = 36.sp
                    )
                }

                Spacer(Modifier.height(4.dp))

                // Хто переміг
                Text(
                    text = if (winner == PieceColor.WHITE)
                        "Перемогли Білі!"
                    else
                        "Перемогли Чорні!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = CheckersColors.TextPrimary,
                    textAlign = TextAlign.Center
                )

                // Привітання
                Text(
                    text = "Вітаємо з перемогою! 🎉",
                    fontSize = 15.sp,
                    color = CheckersColors.TextSecondary,
                    textAlign = TextAlign.Center
                )

                // Статистика
                Text(
                    text = "Ходів у партії: $moveCount",
                    fontSize = 14.sp,
                    color = CheckersColors.TextSecondary
                )

                Spacer(Modifier.height(12.dp))

                // Кнопки
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onClose,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CheckersColors.ButtonSecondary
                        )
                    ) {
                        Text("Закрити", fontSize = 14.sp)
                    }

                    Button(
                        onClick = onNewGame,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CheckersColors.ButtonPrimary
                        )
                    ) {
                        Text("Нова гра", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

