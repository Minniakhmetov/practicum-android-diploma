package ru.practicum.android.diploma.vacancy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.AppIconButton
import ru.practicum.android.diploma.core.designsystem.components.AppLoader
import ru.practicum.android.diploma.core.designsystem.components.AppTopBar
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyState

// Экран вакансии. Нижней панели навигации здесь нет, поэтому отступы под обе системные панели.
// Пока минимальный: вёрстка вакансии - в #70 и #71, плейсхолдеры ошибок с иллюстрациями - в #72
@Composable
fun VacancyScreen(
    state: VacancyState,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background)
            .systemBarsPadding(),
    ) {
        AppTopBar(
            title = stringResource(R.string.vacancy_title),
            onBackClick = onBackClick,
            backContentDescription = stringResource(R.string.back),
        ) {
            // Делиться можно только загруженной вакансией
            if (state is VacancyState.Content) {
                AppIconButton(
                    iconRes = R.drawable.ic_sharing_24,
                    contentDescription = stringResource(R.string.vacancy_share),
                    onClick = onShareClick,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.screenHorizontalPadding),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                VacancyState.Loading -> AppLoader()
                is VacancyState.Content -> VacancyStateText(text = state.vacancy.name)
                VacancyState.NoInternet -> VacancyStateText(text = stringResource(R.string.no_internet))
                VacancyState.ServerError -> VacancyStateText(text = stringResource(R.string.server_error))
                VacancyState.NotFound -> VacancyStateText(text = stringResource(R.string.vacancy_not_found))
            }
        }
    }
}

@Composable
private fun VacancyStateText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = DiplomaTheme.colors.primaryText,
    )
}
