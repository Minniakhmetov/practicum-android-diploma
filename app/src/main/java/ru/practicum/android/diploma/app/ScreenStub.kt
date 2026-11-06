package ru.practicum.android.diploma.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Временные заглушки экранов (#41) для проверки навигации.
// Удаляется, когда во всех фичах появятся настоящие экраны.

private const val STUB_VACANCY_ID = "0000347f-1440-3f6f-a91a-d52c15a41021"

private class StubDestination(val title: String, val destinationId: Int)

private val stubDestinations = listOf(
    StubDestination("Поиск", R.id.searchFragment),
    StubDestination("Избранное", R.id.favoritesFragment),
    StubDestination("Команда", R.id.teamFragment),
    StubDestination("Вакансия", R.id.vacancyFragment),
    StubDestination("Фильтр", R.id.filterFragment),
)

// ComposeView с заглушкой для onCreateView фрагмента
internal fun Fragment.screenStubView(title: String): ComposeView =
    ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        val navController = findNavController()
        setContent {
            DiplomaTheme {
                ScreenStub(title = title, navController = navController)
            }
        }
    }

@Composable
private fun ScreenStub(
    title: String,
    navController: NavController,
) {
    val currentId = navController.currentDestination?.id
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background)
            .safeDrawingPadding()
            .padding(Dimens.screenHorizontalPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.spacing24),
            style = MaterialTheme.typography.titleLarge,
            color = DiplomaTheme.colors.primaryText,
            textAlign = TextAlign.Center,
        )
        stubDestinations
            .filter { it.destinationId != currentId }
            .forEach { destination ->
                Button(
                    onClick = { navController.navigate(destination.destinationId, destination.args()) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = destination.title)
                }
            }
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = DiplomaTheme.colors.primaryText),
        ) {
            Text(text = "Назад")
        }
    }
}

private fun StubDestination.args() =
    if (destinationId == R.id.vacancyFragment) bundleOf("vacancyId" to STUB_VACANCY_ID) else null
