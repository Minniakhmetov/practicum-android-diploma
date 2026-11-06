package ru.practicum.android.diploma.team.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.app.screenStubView
import ru.practicum.android.diploma.app.stubTabButtons

// Заглушка экрана (#41, #42), заменяется настоящим экраном в задачах фичи
class TeamFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = screenStubView(title = "Команда", buttons = stubTabButtons(findNavController()))
}
