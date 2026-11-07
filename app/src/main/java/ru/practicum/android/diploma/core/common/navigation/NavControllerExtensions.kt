package ru.practicum.android.diploma.core.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

// Переход по action только если он есть у текущего экрана.
// Защищает от падения при двойном нажатии: после первого перехода action уже не найден.
fun NavController.navigateSafely(directions: NavDirections) {
    if (currentDestination?.getAction(directions.actionId) != null) {
        navigate(directions)
    }
}
