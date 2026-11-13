package ru.practicum.android.diploma.vacancy.presentation.ui

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
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyViewModel

class VacancyFragment : Fragment() {

    private val args: VacancyFragmentArgs by navArgs()
    private val viewModel: VacancyViewModel by viewModel { parametersOf(args.vacancyId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DiplomaTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
                VacancyScreen(
                    state = state,
                    isFavorite = isFavorite,
                    onBackClick = ::navigateBack,
                    onShareClick = viewModel::onShareClick,
                    onFavoriteClick = viewModel::onFavoriteClick,
                    onEmailClick = viewModel::onEmailClick,
                    onPhoneClick = viewModel::onPhoneClick,
                )
            }
        }
    }

    // Защита от двойного нажатия "Назад": после первого нажатия экран уже не RESUMED
    private fun navigateBack() {
        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            findNavController().navigateUp()
        }
    }
}
