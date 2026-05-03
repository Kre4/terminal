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
import kmpterminal.composeapp.generated.resources.Res
import kmpterminal.composeapp.generated.resources.aws_s3_logo
import kmpterminal.composeapp.generated.resources.flyway_logo
import kmpterminal.composeapp.generated.resources.gradle_logo
import kmpterminal.composeapp.generated.resources.hibernate_logo
import kmpterminal.composeapp.generated.resources.java_logo
import kmpterminal.composeapp.generated.resources.junit5_logo
import kmpterminal.composeapp.generated.resources.kafka_logo
import kmpterminal.composeapp.generated.resources.kotlin_logo_simplified
import kmpterminal.composeapp.generated.resources.liquibase_logo
import kmpterminal.composeapp.generated.resources.maven_logo
import kmpterminal.composeapp.generated.resources.postgresql_logo
import kmpterminal.composeapp.generated.resources.rabbitmq_logo
import kmpterminal.composeapp.generated.resources.spring_boot_logo
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random

private data class FallingIconConfig(
    val painterIndex: Int,
    val xFraction: Float,
    val sizeDp: Float,
    val rotationDeg: Float,
    val alpha: Float,
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
                delayMillis = delayMs,
                easing = LinearEasing,
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
    val iconCount: Int = 40;
    val iconSizeRange: ClosedRange<Float> = 32f..72f;
    val baseDurationMs: Int = 40_000;

    val configs = remember(iconCount, baseDurationMs) {
        val rng = Random(seed = 42)
        List(iconCount) {
            val speedFactor = 0.6f + 0.5f * 0.8f   // 0.6 … 1.4
            val duration = (baseDurationMs * speedFactor).toInt()
            val delay = (rng.nextFloat() * duration).toInt()
            FallingIconConfig(
                painterIndex = rng.nextInt(painters.size),
                xFraction = rng.nextFloat(),
                sizeDp = iconSizeRange.start +
                        rng.nextFloat() * (iconSizeRange.endInclusive - iconSizeRange.start),
                rotationDeg = -15f + rng.nextFloat() * 30f,
                alpha = 0.55f + rng.nextFloat() * 0.45f,
                durationMs = duration,
                delayMs = delay,
            )
        }
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

                configs.forEachIndexed { index, icon ->
                    val progress = progressValues[index]
                    val painter = painters[icon.painterIndex]
                    val iconSizePx = with(density) { icon.sizeDp.dp.toPx() }
                    val x = icon.xFraction * (canvasW - iconSizePx)
                    val y = progress * (canvasH + iconSizePx) - iconSizePx - 20

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

