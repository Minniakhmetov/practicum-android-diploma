package ru.practicum.android.diploma.vacancy.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.AppIconButton
import ru.practicum.android.diploma.core.designsystem.components.AppLoader
import ru.practicum.android.diploma.core.designsystem.components.AppTopBar
import ru.practicum.android.diploma.core.designsystem.components.Placeholder
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.vacancy.presentation.state.VacancyState

// Экран вакансии. Нижней панели навигации здесь нет, поэтому отступы под обе системные панели
@Composable
fun VacancyScreen(
    state: VacancyState,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
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
                FavoriteButton(isFavorite = isFavorite, onClick = onFavoriteClick)
            }
        }
        when (state) {
            is VacancyState.Content -> VacancyContent(
                state = state,
                onEmailClick = onEmailClick,
                onPhoneClick = onPhoneClick,
            )
            VacancyState.Loading -> CenteredState { AppLoader() }
            // Отдельного макета "нет интернета" для вакансии нет: иллюстрация как на поиске (экран 5).
            // Показ избранной вакансии из БД без интернета - в #78
            VacancyState.NoInternet -> VacancyPlaceholder(
                image = R.drawable.img_no_internet_328x223,
                text = stringResource(R.string.no_internet),
            )
            // Экран 40
            VacancyState.ServerError -> VacancyPlaceholder(
                image = R.drawable.img_server_error_vacancy_328x223,
                text = stringResource(R.string.server_error),
            )
            // Экран 30.1: сервер ответил 404
            VacancyState.NotFound -> VacancyPlaceholder(
                image = R.drawable.img_vacancy_not_found_328x223,
                text = stringResource(R.string.vacancy_not_found),
            )
        }
    }
}

// Весь контент прокручивается. По макету: 24 от верхней панели и 24 между разделами
@Composable
private fun VacancyContent(
    state: VacancyState.Content,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
) {
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
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing24),
    ) {
        VacancyMainInfo(vacancy = state.vacancy, salary = state.salary)
        VacancyDescription(html = state.vacancy.description)
        VacancySkills(skills = state.vacancy.skills)
        VacancyContacts(
            contacts = state.vacancy.contacts,
            onEmailClick = onEmailClick,
            onPhoneClick = onPhoneClick,
        )
    }
}

// Невыбранная - контур цвета текста, выбранная - красная заливка (Red #F56B6C)
@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    if (isFavorite) {
        AppIconButton(
            iconRes = R.drawable.ic_favorites_on_24,
            contentDescription = stringResource(R.string.vacancy_remove_from_favorites),
            onClick = onClick,
            tint = DiplomaTheme.colors.attention,
        )
    } else {
        AppIconButton(
            iconRes = R.drawable.ic_favorites_off_24,
            contentDescription = stringResource(R.string.vacancy_add_to_favorites),
            onClick = onClick,
        )
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

// Плейсхолдер по центру области под верхней панелью, кнопка "Назад" остается доступной
@Composable
private fun VacancyPlaceholder(
    @DrawableRes image: Int,
    text: String,
) {
    CenteredState {
        Placeholder(image = image, text = text)
    }
}
