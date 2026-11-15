package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.core.common.navigation.NavigationKeys
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel

class FilterFragment : Fragment() {

    private val viewModel: FilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DiplomaTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                FilterScreen(
                    state = state,
                    onBackClick = ::navigateBack,
                    onSalaryChange = viewModel::onSalaryChange,
                    onSalaryClearClick = viewModel::onSalaryClear,
                    onOnlyWithSalaryChange = viewModel::onOnlyWithSalaryChange,
                    onApplyClick = ::applyFilter,
                    onResetClick = viewModel::onResetClick,
                )
            }
        }
    }

    // "Применить": флаг экрану поиска и возврат назад. "Назад" возвращает без флага.
    // Настройки к этому моменту уже сохранены ViewModel
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
