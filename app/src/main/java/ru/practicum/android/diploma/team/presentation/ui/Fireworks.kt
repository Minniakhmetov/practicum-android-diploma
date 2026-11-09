package ru.practicum.android.diploma.team.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.core.designsystem.theme.Blue
import ru.practicum.android.diploma.core.designsystem.theme.Red
import ru.practicum.android.diploma.core.designsystem.theme.White
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

// Фейерверк на экране "Команда": ракеты по очереди взлетают снизу и разрываются на искры,
// искры разлетаются, тормозят, падают под действием гравитации и гаснут.
// Время читается только в фазе отрисовки Canvas, поэтому каждый кадр перерисовывается
// только этот слой, без рекомпозиции экрана. Касания слой не перехватывает.

private const val BURST_COUNT = 8
private const val BURST_INTERVAL_SEC = 0.3f
private const val ROCKET_RISE_SEC = 0.7f
private const val PARTICLES_PER_BURST = 64
private const val PARTICLE_LIFE_SEC = 1.8f
private const val TOTAL_DURATION_SEC =
    (BURST_COUNT - 1) * BURST_INTERVAL_SEC + ROCKET_RISE_SEC + PARTICLE_LIFE_SEC

// Точка взрыва в долях ширины и высоты области. Залпы чередуются: левая и правая половины экрана
private const val EXPLODE_X_MIN = 0.1f
private const val EXPLODE_X_RANGE = 0.3f
private const val EXPLODE_X_HALF = 0.5f
private const val EXPLODE_Y_MIN = 0.12f
private const val EXPLODE_Y_RANGE = 0.43f

// Скорость искр (dp/с), торможение (1/с) и гравитация (dp/с²)
private const val SPEED_MIN_DP = 150f
private const val SPEED_RANGE_DP = 200f
private const val DRAG = 1.8f
private const val GRAVITY_DP = 140f

// Каждая четвёртая искра белая, чтобы взрыв "искрил"
private const val SPARKLE_EVERY = 4
private const val ANGLE_JITTER = 0.15f
private const val TRAIL_SEC = 0.08f
private const val ROCKET_TRAIL_DP = 18f
private const val PARTICLE_STROKE_DP = 3f
private const val ROCKET_STROKE_DP = 3f
private const val NANOS_IN_SECOND = 1_000_000_000f
private const val SEED = 46

private val burstColors = listOf(
    Blue,
    Red,
    Color(color = 0xFFFFD54F),
    Color(color = 0xFF4CD964),
    Color(color = 0xFFB455FF),
    Color(color = 0xFF29B6F6),
)

private class Particle(val angle: Float, val speedDp: Float, val color: Color)

private class Burst(
    val startSec: Float,
    val x: Float,
    val y: Float,
    val color: Color,
    val particles: List<Particle>,
)

@Composable
fun Fireworks(
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bursts = remember { createBursts(Random(SEED)) }
    var timeSec by remember { mutableFloatStateOf(0f) }
    val currentOnFinished by rememberUpdatedState(onFinished)

    LaunchedEffect(Unit) {
        val startNanos = withFrameNanos { it }
        while (timeSec < TOTAL_DURATION_SEC) {
            timeSec = withFrameNanos { (it - startNanos) / NANOS_IN_SECOND }
        }
        currentOnFinished()
    }

    Canvas(modifier = modifier) {
        val time = timeSec
        bursts.forEach { burst -> drawBurst(burst, time - burst.startSec) }
    }
}

private fun createBursts(random: Random): List<Burst> =
    List(BURST_COUNT) { index ->
        val color = burstColors[index % burstColors.size]
        Burst(
            startSec = index * BURST_INTERVAL_SEC,
            x = index % 2 * EXPLODE_X_HALF + EXPLODE_X_MIN + random.nextFloat() * EXPLODE_X_RANGE,
            y = EXPLODE_Y_MIN + random.nextFloat() * EXPLODE_Y_RANGE,
            color = color,
            particles = List(PARTICLES_PER_BURST) { i ->
                Particle(
                    angle = (i.toFloat() / PARTICLES_PER_BURST + random.nextFloat() * ANGLE_JITTER) *
                        2 * PI.toFloat(),
                    speedDp = SPEED_MIN_DP + random.nextFloat() * SPEED_RANGE_DP,
                    color = if (i % SPARKLE_EVERY == 0) White else color,
                )
            },
        )
    }

private fun DrawScope.drawBurst(burst: Burst, localSec: Float) {
    when {
        localSec < 0f -> Unit
        localSec < ROCKET_RISE_SEC -> drawRocket(burst, localSec / ROCKET_RISE_SEC)
        localSec < ROCKET_RISE_SEC + PARTICLE_LIFE_SEC -> drawSparks(burst, localSec - ROCKET_RISE_SEC)
    }
}

// Ракета летит от нижнего края к точке взрыва и замедляется к концу подъёма
private fun DrawScope.drawRocket(burst: Burst, progress: Float) {
    val eased = 1f - (1f - progress) * (1f - progress)
    val x = burst.x * size.width
    val y = size.height + (burst.y * size.height - size.height) * eased
    drawLine(
        color = burst.color,
        start = Offset(x, y + ROCKET_TRAIL_DP.dp.toPx()),
        end = Offset(x, y),
        strokeWidth = ROCKET_STROKE_DP.dp.toPx(),
        cap = StrokeCap.Round,
    )
}

// Искры рисуются короткими штрихами от положения чуть раньше к текущему: так виден след движения
private fun DrawScope.drawSparks(burst: Burst, sec: Float) {
    val center = Offset(burst.x * size.width, burst.y * size.height)
    val alpha = 1f - sec / PARTICLE_LIFE_SEC
    val gravity = GRAVITY_DP.dp.toPx()
    val strokeWidth = PARTICLE_STROKE_DP.dp.toPx()
    burst.particles.forEach { particle ->
        val speed = particle.speedDp.dp.toPx()
        drawLine(
            color = particle.color.copy(alpha = alpha),
            start = center + sparkOffset(particle.angle, speed, gravity, (sec - TRAIL_SEC).coerceAtLeast(0f)),
            end = center + sparkOffset(particle.angle, speed, gravity, sec),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

// Пройденный путь с торможением: speed * (1 - e^(-k*t)) / k, плюс падение g*t²/2
private fun sparkOffset(angle: Float, speed: Float, gravity: Float, sec: Float): Offset {
    val distance = speed * (1f - exp(-DRAG * sec)) / DRAG
    return Offset(
        x = cos(angle) * distance,
        y = sin(angle) * distance + gravity * sec * sec / 2,
    )
}
