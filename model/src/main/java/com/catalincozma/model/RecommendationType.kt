package com.catalincozma.model

enum class RecommendationType(val path: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming")
}