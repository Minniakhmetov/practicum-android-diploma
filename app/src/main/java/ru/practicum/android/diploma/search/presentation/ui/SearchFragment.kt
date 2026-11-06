package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.STUB_VACANCY_ID
import ru.practicum.android.diploma.app.StubButton
import ru.practicum.android.diploma.app.screenStubView
import ru.practicum.android.diploma.core.common.navigation.NavigationKeys
import ru.practicum.android.diploma.core.common.navigation.navigateSafely

// Заглушка экрана (#41, #42), заменяется настоящим экраном в задачах фичи
class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val navController = findNavController()
        val buttons = listOf(
            StubButton(getString(R.string.vacancy_title)) {
                navController.navigateSafely(
                    SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancyId = STUB_VACANCY_ID),
                )
            },
            StubButton(getString(R.string.filter_title)) {
                navController.navigateSafely(SearchFragmentDirections.actionSearchFragmentToFilterFragment())
            },
        )
        return screenStubView(title = getString(R.string.search_title), buttons = buttons)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Флаг от экрана фильтра: после "Применить" поиск нужно повторить
        val savedStateHandle = findNavController().getBackStackEntry(R.id.searchFragment).savedStateHandle
        savedStateHandle.getLiveData<Boolean>(NavigationKeys.FILTER_APPLIED).observe(viewLifecycleOwner) { applied ->
            if (applied) {
                Log.d(TAG, "${NavigationKeys.FILTER_APPLIED} received")
                savedStateHandle.remove<Boolean>(NavigationKeys.FILTER_APPLIED)
            }
        }
    }

    private companion object {
        const val TAG = "SearchFragment"
    }
}
