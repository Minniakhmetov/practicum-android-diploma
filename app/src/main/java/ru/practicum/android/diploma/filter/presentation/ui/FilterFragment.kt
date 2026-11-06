package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.app.StubButton
import ru.practicum.android.diploma.app.screenStubView
import ru.practicum.android.diploma.app.stubBackButton
import ru.practicum.android.diploma.core.common.navigation.NavigationKeys

// Заглушка экрана (#41, #42), заменяется настоящим экраном в задачах фичи
class FilterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val navController = findNavController()
        val buttons = listOf(
            // "Применить": флаг экрану поиска и возврат назад
            StubButton("Применить") {
                navController.previousBackStackEntry?.savedStateHandle?.set(NavigationKeys.FILTER_APPLIED, true)
                navController.popBackStack()
            },
            // "Назад": возврат без флага
            stubBackButton(navController),
        )
        return screenStubView(title = "Настройки фильтрации", buttons = buttons)
    }
}
