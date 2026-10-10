package com.example.checkers.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Палітра кольорів для гри в шашки.
 */
object CheckersColors {
    // Дошка
    val LightCell = Color(0xFFF0D9B5)
    val DarkCell  = Color(0xFF8B5A2B)

    // Підсвічування
    val SelectedCell = Color(0x88FFD700)   // жовтий — обрана фігура
    val MoveHint     = Color(0x8832CD32)   // зелений — простий хід
    val CaptureHint  = Color(0x88FF4500)   // помаранчевий — взяття
    val LastMove     = Color(0x66ADD8E6)   // блакитний — останній хід

    // Фігури
    val WhitePiece       = Color(0xFFF5F5F5)
    val WhitePieceBorder = Color(0xFF888888)
    val BlackPiece       = Color(0xFF1A1A1A)
    val BlackPieceBorder = Color(0xFF444444)
    val Crown            = Color(0xFFDAA520)

    // UI
    val Background      = Color(0xFF2B2B2B)
    val Panel           = Color(0xFF3C3C3C)
    val TextPrimary     = Color(0xFFEDEDED)
    val TextSecondary   = Color(0xFFAAAAAA)
    val ButtonPrimary   = Color(0xFF4A7C59)
    val ButtonSecondary = Color(0xFF6B4E3D)
    val ButtonDisabled  = Color(0xFF555555)
}

