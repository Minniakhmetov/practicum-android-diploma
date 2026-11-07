package ru.practicum.android.diploma.vacancy.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.screenStubView

// Заглушка экрана (#41, #42), заменяется настоящим экраном в задачах фичи
class VacancyFragment : Fragment() {

    private val args: VacancyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = screenStubView(
        title = getString(R.string.vacancy_title) + "\n" + args.vacancyId,
        buttons = emptyList(),
    )
}
