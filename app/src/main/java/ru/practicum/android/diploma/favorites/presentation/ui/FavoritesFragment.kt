package ru.practicum.android.diploma.favorites.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.STUB_VACANCY_ID
import ru.practicum.android.diploma.app.StubButton
import ru.practicum.android.diploma.app.screenStubView
import ru.practicum.android.diploma.core.common.navigation.navigateSafely

// Заглушка экрана (#41, #42), заменяется настоящим экраном в задачах фичи
class FavoritesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val navController = findNavController()
        val buttons = listOf(
            StubButton(getString(R.string.vacancy_title)) {
                navController.navigateSafely(
                    FavoritesFragmentDirections.actionFavoritesFragmentToVacancyFragment(vacancyId = STUB_VACANCY_ID),
                )
            },
        )
        return screenStubView(title = getString(R.string.favorites), buttons = buttons)
    }
}
