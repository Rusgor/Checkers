package com.example.checkers.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Одна частинка феєрверку.
 * @param startX початкова позиція по X (0..1 — відносно ширини)
 * @param startY початкова позиція по Y
 * @param angle кут розльоту (радіани)
 * @param speed швидкість
 * @param color колір
 * @param delayMs затримка перед стартом (мс)
 */
private data class Particle(
    val startX: Float,
    val startY: Float,
    val angle: Float,
    val speed: Float,
    val color: Color,
    val delayMs: Int,
    val size: Float
)

/**
 * Компонент, який малює анімований феєрверк на весь екран.
 * Запускається автоматично при появі.
 */
@Composable
fun Fireworks(
    modifier: Modifier = Modifier,
    burstCount: Int = 5,
    particlesPerBurst: Int = 25
) {
    // Генеруємо всі частинки один раз при створенні
    val particles = remember {
        buildList {
            repeat(burstCount) { burstIndex ->
                // Центр кожного "залпу" — випадкова точка у верхній частині
                val centerX = 0.2f + Random.nextFloat() * 0.6f
                val centerY = 0.15f + Random.nextFloat() * 0.35f
                val burstDelay = burstIndex * 350

                repeat(particlesPerBurst) { i ->
                    val angle = (i.toFloat() / particlesPerBurst) * 2f * Math.PI.toFloat()
                    // Невеликий розкид кута
                    val angleJitter = (Random.nextFloat() - 0.5f) * 0.3f
                    val speed = 0.3f + Random.nextFloat() * 0.35f
                    val color = FIREWORK_COLORS.random()

                    add(
                        Particle(
                            startX = centerX,
                            startY = centerY,
                            angle = angle + angleJitter,
                            speed = speed,
                            color = color,
                            delayMs = burstDelay,
                            size = 3f + Random.nextFloat() * 4f
                        )
                    )
                }
            }
        }
    }

    // Анімація: 0 → 1 за 2.5 секунди
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2500, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val now = progress.value * 2500f  // скільки мс минуло

        for (p in particles) {
            // Частинка ще не стартувала
            if (now < p.delayMs) continue

            val localTime = (now - p.delayMs) / 1000f  // секунди

            // Позиція: старт + рух за кутом і швидкістю
            // Гравітація: y трохи збільшується з часом
            val dx = cos(p.angle) * p.speed * localTime
            val dy = sin(p.angle) * p.speed * localTime + 0.3f * localTime * localTime

            val x = p.startX * size.width + dx * size.width * 0.7f
            val y = p.startY * size.height + dy * size.height * 0.7f

            // Затухання прозорості
            val alpha = (1f - localTime / 1.8f).coerceIn(0f, 1f)

            drawCircle(
                color = p.color.copy(alpha = alpha),
                radius = p.size,
                center = Offset(x, y)
            )
        }
    }
}

private val FIREWORK_COLORS = listOf(
    Color(0xFFFFD700), // золотий
    Color(0xFFFF4500), // оранжево-червоний
    Color(0xFF32CD32), // зелений
    Color(0xFF00BFFF), // блакитний
    Color(0xFFFF69B4), // рожевий
    Color(0xFFFFFFFF)  // білий
)

