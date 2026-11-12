package ru.practicum.android.diploma.search.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.result.ErrorType
import ru.practicum.android.diploma.core.designsystem.components.AppChip
import ru.practicum.android.diploma.core.designsystem.components.AppIconButton
import ru.practicum.android.diploma.core.designsystem.components.AppLoader
import ru.practicum.android.diploma.core.designsystem.components.AppTopBar
import ru.practicum.android.diploma.core.designsystem.components.LightDarkPreviews
import ru.practicum.android.diploma.core.designsystem.components.Placeholder
import ru.practicum.android.diploma.core.designsystem.components.SearchField
import ru.practicum.android.diploma.core.designsystem.components.VacancyListItem
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.VacancyShort
import ru.practicum.android.diploma.search.presentation.state.SearchResultState
import ru.practicum.android.diploma.search.presentation.state.SearchState
import ru.practicum.android.diploma.search.presentation.state.VacancyShortItem

@Composable
fun SearchScreen(
    state: SearchState,
    onVacancyClick: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearch: () -> Unit,
    onLastItemReached: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTopBar(
            title = stringResource(R.string.search_title),
            onBackClick = null,
            backContentDescription = null,
        ) {
            AppIconButton(iconRes = R.drawable.ic_filter_off_24, contentDescription = null, onClick = {})
        }

        SearchField(
            query = state.query,
            onQueryChange = onQueryChange,
            onClearClick = onClearClick,
            placeholder = stringResource(R.string.search_hint),
            clearContentDescription = stringResource(R.string.search_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.spacing16,
                    vertical = Dimens.spacing8,
                ),
            onSearch = onSearch,
        )

        Box(
            modifier = Modifier.weight(1f),
        ) {
            when (val result = state.result) {
                SearchResultState.Initial -> Placeholder(
                    image = R.drawable.img_search_start_328x223,
                    text = null,
                    modifier = Modifier.fillMaxSize(),
                )

                SearchResultState.Loading -> AppLoader(
                    modifier = Modifier.fillMaxSize(),
                )

                is SearchResultState.Error -> ErrorContent(
                    type = result.type,
                )

                is SearchResultState.Content -> Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AppChip(
                        text = pluralStringResource(
                            R.plurals.search_vacancies_found,
                            result.found,
                            result.found,
                        ),
                        modifier = Modifier.padding(
                            top = Dimens.spacing4,
                        ),
                    )

                    SearchVacanciesList(
                        vacancies = result.vacancies,
                        isNextPageLoading = result.isNextPageLoading,
                        onVacancyClick = onVacancyClick,
                        onLastItemReached = onLastItemReached,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    type: ErrorType,
) {
    when (type) {
        ErrorType.NotFound -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.screenHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppChip(
                text = stringResource(R.string.search_no_vacancies),
                modifier = Modifier.padding(top = Dimens.spacing4),
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Placeholder(
                    image = R.drawable.img_empty_list_328x223,
                    text = stringResource(R.string.failed_to_get_vacancies),
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        ErrorType.NoInternet -> Placeholder(
            image = R.drawable.img_no_internet_328x223,
            text = stringResource(R.string.no_internet),
            modifier = Modifier.fillMaxSize(),
        )

        ErrorType.Server -> Placeholder(
            image = R.drawable.img_server_error_search_328x223,
            text = stringResource(R.string.server_error),
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun SearchVacanciesList(
    vacancies: List<VacancyShortItem>,
    isNextPageLoading: Boolean,
    onVacancyClick: (String) -> Unit,
    onLastItemReached: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    // true, когда последний видимый элемент — последний в списке (включая лоадер).
    val isLastItemVisible by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = layoutInfo.totalItemsCount
            lastVisibleIndex != null && totalItemsCount > 0 && lastVisibleIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(isLastItemVisible) {
        if (isLastItemVisible) {
            onLastItemReached()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimens.spacing8),
        contentPadding = PaddingValues(bottom = Dimens.spacing8),
    ) {
        items(
            items = vacancies,
            key = { it.vacancy.id },
        ) { item ->
            VacancyListItem(
                name = item.vacancy.name,
                area = item.vacancy.area,
                employerName = item.vacancy.employerName,
                salary = item.salary,
                logoUrl = item.vacancy.logoUrl,
                onClick = {
                    onVacancyClick(item.vacancy.id)
                },
            )
        }
        if (isNextPageLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.spacing16),
                    contentAlignment = Alignment.Center,
                ) {
                    AppLoader()
                }
            }
        }
    }
}

private class SearchStatePreviewProvider : PreviewParameterProvider<SearchState> {
    override val values = sequenceOf(
        SearchState(
            query = "",
            result = SearchResultState.Initial,
        ),
        SearchState(
            query = "Разоаботчик",
            result = SearchResultState.Loading,
        ),
        SearchState(
            query = "Android-разработчик",
            result = SearchResultState.Content(
                found = PREVIEW_VACANCIES_COUNT,
                vacancies = List(PREVIEW_VACANCIES_COUNT) { index ->
                    VacancyShortItem(
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
                hasMore = false
            ),
        ),
        SearchState(
            query = "Android",
            result = SearchResultState.Error(
                type = ErrorType.NoInternet,
            ),
        ),
    )
}

private const val PREVIEW_VACANCIES_COUNT = 3

@LightDarkPreviews
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchStatePreviewProvider::class) state: SearchState,
) {
    DiplomaTheme {
        SearchScreen(
            state = state,
            onVacancyClick = {},
            onQueryChange = {},
            onClearClick = {},
            onSearch = {},
            onLastItemReached = {}
        )
    }
}
