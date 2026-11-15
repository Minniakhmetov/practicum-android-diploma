package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.core.common.navigation.NavigationKeys
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.filter.presentation.state.FilterState

class FilterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DiplomaTheme {
                // Временное состояние экрана до FilterViewModel (#90): значения живут, пока открыт экран
                var salary by rememberSaveable { mutableStateOf("") }
                var onlyWithSalary by rememberSaveable { mutableStateOf(false) }
                FilterScreen(
                    state = FilterState(salary = salary, onlyWithSalary = onlyWithSalary),
                    onBackClick = ::navigateBack,
                    onSalaryChange = { salary = it },
                    onSalaryClearClick = { salary = "" },
                    onOnlyWithSalaryChange = { onlyWithSalary = it },
                    onApplyClick = ::applyFilter,
                    onResetClick = {
                        salary = ""
                        onlyWithSalary = false
                    },
                )
            }
        }
    }

    // "Применить": флаг экрану поиска и возврат назад. "Назад" возвращает без флага
    private fun applyFilter() {
        if (isScreenResumed()) {
            val navController = findNavController()
            navController.previousBackStackEntry?.savedStateHandle?.set(NavigationKeys.FILTER_APPLIED, true)
            navController.popBackStack()
        }
    }

    private fun navigateBack() {
        if (isScreenResumed()) {
            findNavController().navigateUp()
        }
    }

    // Защита от двойного нажатия: после первого перехода экран уже не RESUMED
    private fun isScreenResumed(): Boolean =
        viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
}
