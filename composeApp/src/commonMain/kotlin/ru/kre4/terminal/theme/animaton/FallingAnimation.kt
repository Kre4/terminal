package ru.kre4.terminal.theme.animaton

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kmpterminal.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

object AnimationConstants {
    const val ICON_SIZE = 46f
    const val ANIMATION_DURATION = 80_000
    const val GRID_COLUMNS = 14
    const val GRID_ROWS = 8
}

data class FallingIconConfig(
    val painterIndex: Int,
    val columnIndex: Int,
    val totalColumns: Int,
    val xJitter: Float,
    val sizeDp: Float = AnimationConstants.ICON_SIZE,
    val rotationDeg: Float,
    val alpha: Float = 0.7f,
    val durationMs: Int,
    val delayMs: Int,
)

@Composable
private fun rememberIconProgress(durationMs: Int, delayMs: Int): Float {
    val transition = rememberInfiniteTransition(label = "icon_fall")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMs,
                easing = LinearEasing,
            ),
            initialStartOffset = StartOffset(
                offsetMillis = delayMs,
                offsetType = StartOffsetType.FastForward,
            )
        ),
        label = "fall_progress",
    )
    return progress
}



@Composable
fun SvgFallAnimation(
    painters: List<Painter> = listOf(
        painterResource(Res.drawable.kotlin_logo_simplified),
        painterResource(Res.drawable.java_logo),
        painterResource(Res.drawable.spring_boot_logo),
        painterResource(Res.drawable.aws_s3_logo),
        painterResource(Res.drawable.flyway_logo),
        painterResource(Res.drawable.gradle_logo),
        painterResource(Res.drawable.hibernate_logo),
        painterResource(Res.drawable.kafka_logo),
        painterResource(Res.drawable.liquibase_logo),
        painterResource(Res.drawable.maven_logo),
        painterResource(Res.drawable.postgresql_logo),
        painterResource(Res.drawable.rabbitmq_logo),

        )
) {

    val configs = remember(AnimationConstants.GRID_COLUMNS) {
        initConfigs(painters, AnimationConstants.ANIMATION_DURATION,
            AnimationConstants.GRID_COLUMNS, AnimationConstants.GRID_ROWS)
    }

    val progressValues = configs.map { cfg ->
        rememberIconProgress(durationMs = cfg.durationMs, delayMs = cfg.delayMs)
    }

    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0f)
            .drawWithContent {
                drawContent()

                val canvasW = size.width
                val canvasH = size.height
                val iconSizePx = with(density) { AnimationConstants.ICON_SIZE.dp.toPx() }
                val cellWidth = canvasW / configs.first().totalColumns

                configs.forEachIndexed { index, icon ->
                    val progress = progressValues[index]
                    val painter = painters[icon.painterIndex]
                    val cellCenter = (icon.columnIndex + 0.5f) * cellWidth
                    val x = cellCenter - iconSizePx / 2f + icon.xJitter * cellWidth

                    val y = progress * (canvasH + iconSizePx) - iconSizePx

                    withTransform(
                        transformBlock = {
                            translate(left = x, top = y)
                            rotate(
                                degrees = icon.rotationDeg,
                                pivot = Offset(iconSizePx / 2f, iconSizePx / 2f),
                            )
                        }
                    ) {
                        with(painter) {
                            draw(
                                size = Size(iconSizePx, iconSizePx),
                                alpha = icon.alpha,
                            )
                        }
                    }
                }
            }
    )

}

fun initConfigs(painters: List<Painter>, baseDurationMs: Int, columns: Int, rows: Int): List<FallingIconConfig> {
    val rng = Random(seed = 53)

    val iconCount = columns * rows

    val painterIndices = (0 until iconCount).map { it % painters.size }

    return List(iconCount) { i ->
        val col = i % columns
        val row = i / columns

        val duration = baseDurationMs
        val rowPhase = (row.toFloat() / rows) * duration
        val jitter = - (rng.nextFloat() - 0.5f) * duration * 0.06f
        val initialOffset = (rowPhase + jitter).toInt()

        FallingIconConfig(
            painterIndex = painterIndices[i],
            columnIndex = col,
            totalColumns = columns,
            xJitter = (rng.nextFloat() - 0.5f) * 0.2f,
            rotationDeg = -15f + rng.nextFloat() * 30f,
            durationMs = baseDurationMs,
            delayMs = initialOffset,
        )
    }
}


