package com.catalincozma.ui.screen

import androidx.annotation.StringRes
import com.catalincozma.ui.R

enum class BottomNavItem(val route: String, @StringRes val labelResId: Int, val iconRes: Int) {
    Favorites("favorites", R.string.favorite, R.drawable.ic_favorite),
    Home("home", R.string.home, R.drawable.movie),
    Search("search", R.string.search, R.drawable.ic_search)
}
