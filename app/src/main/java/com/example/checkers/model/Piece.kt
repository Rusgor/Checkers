package com.example.checkers.model

data class Piece(
    val color: PieceColor,
    val type: PieceType = PieceType.MAN
) {
    val isKing: Boolean get() = type == PieceType.KING
    val isMan: Boolean get() = type == PieceType.MAN
}

