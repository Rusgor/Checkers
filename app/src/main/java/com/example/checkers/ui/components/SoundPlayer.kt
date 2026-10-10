package com.example.checkers.ui.components

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper

/**
 * Простий програвач звуків через ToneGenerator.
 * Не потребує зовнішніх файлів — грає "фанфари" із системних тонів.
 */
object SoundPlayer {

    private var toneGenerator: ToneGenerator? = null

    /**
     * Грає короткі фанфари: три висхідні ноти.
     * Активно використовується після перемоги.
     */
    fun playVictoryFanfare() {
        try {
            if (toneGenerator == null) {
                toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
            }
            val tg = toneGenerator ?: return

            // Висхідна мелодія: C5 → E5 → G5 → C6
            val handler = Handler(Looper.getMainLooper())

            tg.startTone(ToneGenerator.TONE_PROP_BEEP, 150)

            handler.postDelayed({
                tg.startTone(ToneGenerator.TONE_PROP_BEEP2, 150)
            }, 180)

            handler.postDelayed({
                tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
            }, 360)

            handler.postDelayed({
                tg.startTone(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE, 350)
            }, 580)
        } catch (e: Exception) {
            // Ігноруємо помилки звуку — гра має працювати й без нього
            android.util.Log.w("SoundPlayer", "Не вдалося відтворити фанфари: ${e.message}")
        }
    }

    /**
     * Короткий клік при ході (опціонально).
     */
    fun playMoveSound() {
        try {
            if (toneGenerator == null) {
                toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 60)
            }
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 60)
        } catch (e: Exception) {
            // Ігноруємо
        }
    }

    /**
     * Звільняє ресурси. Викликати при закритті гри.
     */
    fun release() {
        try {
            toneGenerator?.release()
            toneGenerator = null
        } catch (e: Exception) {
            // Ігноруємо
        }
    }
}