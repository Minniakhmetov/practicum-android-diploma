package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.StubButton
import ru.practicum.android.diploma.app.screenStubView
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
            // "Применить": флаг экрану поиска и возврат назад. Системная кнопка "Назад" возвращает без флага
            StubButton(getString(R.string.filter_apply)) {
                navController.previousBackStackEntry?.savedStateHandle?.set(NavigationKeys.FILTER_APPLIED, true)
                navController.popBackStack()
            },
        )
        return screenStubView(title = getString(R.string.filter_title), buttons = buttons)
    }
}
