package com.example.checkers.model

data class Move(
    val from: Position,
    val to: Position,
    val captured: List<Position> = emptyList()
) {
    val isCapture: Boolean get() = captured.isNotEmpty()
}

