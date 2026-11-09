package ru.practicum.android.diploma.vacancy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
// Описание, навыки и контакты - в #71, плейсхолдеры ошибок с иллюстрациями - в #72
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
            // Делиться и добавлять в избранное можно только загруженную вакансию
            if (state is VacancyState.Content) {
                AppIconButton(
                    iconRes = R.drawable.ic_sharing_24,
                    contentDescription = stringResource(R.string.vacancy_share),
                    onClick = onShareClick,
                )
                // Пока только иконка: добавление в избранное - в #77
                AppIconButton(
                    iconRes = R.drawable.ic_favorites_off_24,
                    contentDescription = stringResource(R.string.vacancy_add_to_favorites),
                    onClick = {},
                )
            }
        }
        when (state) {
            is VacancyState.Content -> VacancyContent(state = state)
            VacancyState.Loading -> CenteredState { AppLoader() }
            VacancyState.NoInternet -> CenteredState { VacancyStateText(stringResource(R.string.no_internet)) }
            VacancyState.ServerError -> CenteredState { VacancyStateText(stringResource(R.string.server_error)) }
            VacancyState.NotFound -> CenteredState { VacancyStateText(stringResource(R.string.vacancy_not_found)) }
        }
    }
}

// Весь контент прокручивается. По макету: 16 от верхней панели + 8 внутреннего отступа блока названия
@Composable
private fun VacancyContent(state: VacancyState.Content) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = Dimens.screenHorizontalPadding,
                end = Dimens.screenHorizontalPadding,
                top = Dimens.spacing24,
                bottom = Dimens.spacing24,
            ),
    ) {
        VacancyMainInfo(vacancy = state.vacancy, salary = state.salary)
    }
}

@Composable
private fun CenteredState(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.screenHorizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        content()
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
