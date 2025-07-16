package com.catalincozma.ui.screen.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

fun scaleFadeTransitions(): EnterTransition {
    return scaleIn(
        initialScale = 0.8f,
        animationSpec = tween(durationMillis = 300)
    ) + fadeIn(animationSpec = tween(300))
}

fun scaleFadeExitTransitions(): ExitTransition {
    return scaleOut(
        targetScale = 0.8f,
        animationSpec = tween(durationMillis = 300)
    ) + fadeOut(animationSpec = tween(300))
}
