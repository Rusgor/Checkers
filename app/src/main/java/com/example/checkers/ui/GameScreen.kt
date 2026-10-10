package com.example.checkers.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.checkers.logic.GameEngine
import com.example.checkers.model.Move
import com.example.checkers.model.Position
import com.example.checkers.ui.components.BoardView
import com.example.checkers.ui.components.Fireworks
import com.example.checkers.ui.components.InfoPanel
import com.example.checkers.ui.components.SoundPlayer
import com.example.checkers.ui.components.WinDialog
import com.example.checkers.ui.theme.CheckersColors

@Composable
fun GameScreen() {
    var engine by remember { mutableStateOf(GameEngine()) }
    var selectedPosition by remember { mutableStateOf<Position?>(null) }
    var lastMove by remember { mutableStateOf<Move?>(null) }
    var moveCount by remember { mutableStateOf(0) }
    var boardVersion by remember { mutableStateOf(0) }

    // Чи показувати діалог перемоги
    var showWinDialog by remember { mutableStateOf(false) }

    val availableMoves = remember(boardVersion, engine.chainFrom) {
        engine.availableMoves()
    }

    val movesFromSelected = remember(selectedPosition, boardVersion) {
        selectedPosition?.let { sel ->
            availableMoves.filter { it.from == sel }
        } ?: emptyList()
    }

    // Реакція на перемогу: показати діалог + феєрверк + фанфари
    LaunchedEffect(engine.winner) {
        engine.winner?.let {
            showWinDialog = true
            SoundPlayer.playVictoryFanfare()
        }
    }

    fun handleCellClick(pos: Position) {
        if (selectedPosition != null) {
            val from = selectedPosition!!
            val currentMoves = engine.availableMoves()
            val move = currentMoves.firstOrNull { it.from == from && it.to == pos }

            if (move != null) {
                if (engine.makeMove(move)) {
                    lastMove = move
                    moveCount++
                    selectedPosition = null
                    boardVersion++
                    SoundPlayer.playMoveSound()
                }
                return
            }
        }

        val piece = engine.board[pos]
        if (piece != null && piece.color == engine.currentColor) {
            val hasMoves = engine.availableMoves().any { it.from == pos }
            selectedPosition = if (hasMoves) pos else null
        } else {
            selectedPosition = null
        }
    }

    fun resetGame() {
        engine = GameEngine()
        selectedPosition = null
        lastMove = null
        moveCount = 0
        boardVersion++
        showWinDialog = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CheckersColors.Background)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            InfoPanel(
                currentColor = engine.currentColor,
                moveCount = moveCount,
                winner = engine.winner,
                isChainActive = engine.chainFrom != null,
                canUndo = moveCount > 0,
                onUndo = {
                    if (engine.undo()) {
                        moveCount = (moveCount - 1).coerceAtLeast(0)
                        selectedPosition = null
                        lastMove = null
                        boardVersion++
                        showWinDialog = false
                    }
                },
                onNewGame = { resetGame() },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BoardView(
                    board = engine.board,
                    selectedPosition = selectedPosition,
                    possibleMoves = movesFromSelected,
                    lastMove = lastMove,
                    onCellClick = ::handleCellClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }

        // Феєрверк поверх усього (тільки коли є переможець)
        if (engine.winner != null) {
            Fireworks(modifier = Modifier.fillMaxSize())
        }
    }

    // Діалог перемоги
    if (showWinDialog && engine.winner != null) {
        WinDialog(
            winner = engine.winner!!,
            moveCount = moveCount,
            onNewGame = { resetGame() },
            onClose = { showWinDialog = false }
        )
    }

    // Звільняємо ресурси звуку при виході з екрана
    DisposableEffect(Unit) {
        onDispose {
            SoundPlayer.release()
        }
    }
}


