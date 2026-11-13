package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.navigation.NavigationKeys
import ru.practicum.android.diploma.core.common.navigation.navigateSafely
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DiplomaTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                SearchScreen(
                    state = state,
                    onVacancyClick = ::openVacancy,
                    onSearch = { viewModel.onSearchClicked() },
                    onQueryChange = { viewModel.onQueryChanged(it) },
                    onClearClick = { viewModel.onQueryChanged("") },
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Флаг от экрана фильтра: после "Применить" поиск нужно повторить
        val savedStateHandle = findNavController().getBackStackEntry(R.id.searchFragment).savedStateHandle
        savedStateHandle.getLiveData<Boolean>(NavigationKeys.FILTER_APPLIED).observe(viewLifecycleOwner) { applied ->
            if (applied) {
                viewModel.onFiltersApplied()
                savedStateHandle.remove<Boolean>(NavigationKeys.FILTER_APPLIED)
            }
        }
    }

    private fun openVacancy(vacancyId: String) {
        findNavController().navigateSafely(
            SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancyId = vacancyId),
        )
    }
}
