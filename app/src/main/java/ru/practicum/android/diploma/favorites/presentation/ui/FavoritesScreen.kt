package ru.practicum.android.diploma.favorites.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.AppTopBar
import ru.practicum.android.diploma.core.designsystem.components.LightDarkPreviews
import ru.practicum.android.diploma.core.designsystem.components.Placeholder
import ru.practicum.android.diploma.core.designsystem.components.VacancyListItem
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.favorites.presentation.state.FavoriteVacancyItem
import ru.practicum.android.diploma.favorites.presentation.state.FavoritesState

// Экран "Избранное" (экраны 43-45). Нижняя панель навигации стоит в RootActivity под экраном,
// поэтому снизу отступ не нужен, только сверху под статус-бар
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background)
            .statusBarsPadding(),
    ) {
        AppTopBar(
            title = stringResource(R.string.favorites),
            onBackClick = null,
            backContentDescription = null,
        )
        when (state) {
            is FavoritesState.Content -> FavoritesList(
                vacancies = state.vacancies,
                onVacancyClick = onVacancyClick,
            )
            // Экран 43
            FavoritesState.Empty -> FavoritesPlaceholder(
                image = R.drawable.img_favorites_empty_328x223,
                text = stringResource(R.string.favorites_empty),
            )
            // Экран 45
            FavoritesState.Error -> FavoritesPlaceholder(
                image = R.drawable.img_empty_list_328x223,
                text = stringResource(R.string.failed_to_get_vacancies),
            )
            // Список читается из БД почти мгновенно: лоадер только мигнул бы, поэтому пусто
            FavoritesState.Loading -> Unit
        }
    }
}

// Экран 44: список начинается в 16 от верхней панели (через contentPadding, чтобы
// при прокрутке карточки уезжали под панель, а не обрезались на отступе).
// id - первичный ключ в БД, поэтому уникален и годится как ключ списка
@Composable
private fun FavoritesList(
    vacancies: List<FavoriteVacancyItem>,
    onVacancyClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = Dimens.spacing16),
    ) {
        items(items = vacancies, key = { it.vacancy.id }) { item ->
            VacancyListItem(
                name = item.vacancy.name,
                area = item.vacancy.area,
                employerName = item.vacancy.employerName,
                salary = item.salary,
                logoUrl = item.vacancy.logoUrl,
                onClick = { onVacancyClick(item.vacancy.id) },
            )
        }
    }
}

// Плейсхолдер по центру области под верхней панелью
@Composable
private fun FavoritesPlaceholder(
    @DrawableRes image: Int,
    text: String,
) {
    Placeholder(
        image = image,
        text = text,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.screenHorizontalPadding),
    )
}

private class FavoritesStatePreviewProvider : PreviewParameterProvider<FavoritesState> {
    override val values = sequenceOf(
        FavoritesState.Content(
            vacancies = List(PREVIEW_VACANCIES_COUNT) { index ->
                FavoriteVacancyItem(
                    vacancy = VacancyShort(
                        id = index.toString(),
                        name = "Android-разработчик",
                        employerName = "Яндекс",
                        area = "Москва",
                        logoUrl = null,
                        salary = null,
                    ),
                    salary = "от 100 000 ₽",
                )
            },
        ),
        FavoritesState.Empty,
        FavoritesState.Error,
    )
}

private const val PREVIEW_VACANCIES_COUNT = 3

@LightDarkPreviews
@Composable
private fun FavoritesScreenPreview(
    @PreviewParameter(FavoritesStatePreviewProvider::class) state: FavoritesState,
) {
    DiplomaTheme {
        FavoritesScreen(state = state, onVacancyClick = {})
    }
}
