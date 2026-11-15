package ru.practicum.android.diploma.favorites.presentation.ui

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
import ru.practicum.android.diploma.core.common.navigation.navigateSafely
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DiplomaTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                FavoritesScreen(
                    state = state,
                    onVacancyClick = ::openVacancy,
                )
            }
        }
    }

    // navigateSafely защищает от двойного нажатия: после первого перехода action уже не найден
    private fun openVacancy(vacancyId: String) {
        findNavController().navigateSafely(
            FavoritesFragmentDirections.actionFavoritesFragmentToVacancyFragment(vacancyId = vacancyId),
        )
    }
}
