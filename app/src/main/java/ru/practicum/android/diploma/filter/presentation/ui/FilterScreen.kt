package ru.practicum.android.diploma.filter.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.AppTopBar
import ru.practicum.android.diploma.core.designsystem.components.CheckboxListItem
import ru.practicum.android.diploma.core.designsystem.components.LightDarkPreviews
import ru.practicum.android.diploma.core.designsystem.components.PrimaryButton
import ru.practicum.android.diploma.core.designsystem.components.SalaryTextField
import ru.practicum.android.diploma.core.designsystem.components.TertiaryButton
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.filter.presentation.state.FilterState

// Экран "Настройки фильтрации" (макеты 10, 23-25). Нижней панели навигации здесь нет,
// поэтому отступы под обе системные панели. Пункты "Место работы" и "Отрасль" не верстаем:
// они вне объёма команды из двух человек
@Composable
fun FilterScreen(
    state: FilterState,
    onBackClick: () -> Unit,
    onSalaryChange: (String) -> Unit,
    onSalaryClearClick: () -> Unit,
    onOnlyWithSalaryChange: (Boolean) -> Unit,
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background)
            .systemBarsPadding(),
    ) {
        AppTopBar(
            title = stringResource(R.string.filter_title),
            onBackClick = onBackClick,
            backContentDescription = stringResource(R.string.back),
        )
        // Параметры прокручиваются, кнопки прижаты к низу экрана
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(top = Dimens.spacing24),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing24),
        ) {
            SalaryTextField(
                value = state.salary,
                onValueChange = onSalaryChange,
                onClearClick = onSalaryClearClick,
                label = stringResource(R.string.filter_salary),
                placeholder = stringResource(R.string.filter_salary_hint),
                clearContentDescription = stringResource(R.string.filter_salary_clear),
                modifier = Modifier.padding(horizontal = Dimens.screenHorizontalPadding),
            )
            CheckboxListItem(
                text = stringResource(R.string.filter_only_with_salary),
                checked = state.onlyWithSalary,
                onCheckedChange = onOnlyWithSalaryChange,
            )
        }
        FilterActions(
            isApplyVisible = state.isApplyVisible,
            isResetVisible = state.isResetVisible,
            onApplyClick = onApplyClick,
            onResetClick = onResetClick,
        )
    }
}

// "Применить" и "Сбросить" друг под другом на всю ширину. После очистки фильтра
// видна только "Применить": чтобы перезапустить поиск без фильтра
@Composable
private fun FilterActions(
    isApplyVisible: Boolean,
    isResetVisible: Boolean,
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit,
) {
    if (!isApplyVisible && !isResetVisible) return
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.screenHorizontalPadding,
                end = Dimens.screenHorizontalPadding,
                bottom = Dimens.spacing24,
            ),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
    ) {
        if (isApplyVisible) {
            PrimaryButton(
                text = stringResource(R.string.filter_apply),
                onClick = onApplyClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (isResetVisible) {
            TertiaryButton(
                text = stringResource(R.string.filter_reset),
                onClick = onResetClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private class FilterStatePreviewProvider : PreviewParameterProvider<FilterState> {
    override val values = sequenceOf(
        FilterState(),
        FilterState(salary = "40000", onlyWithSalary = true),
        FilterState(isChanged = true),
    )
}

@LightDarkPreviews
@Composable
private fun FilterScreenPreview(
    @PreviewParameter(FilterStatePreviewProvider::class) state: FilterState,
) {
    DiplomaTheme {
        FilterScreen(
            state = state,
            onBackClick = {},
            onSalaryChange = {},
            onSalaryClearClick = {},
            onOnlyWithSalaryChange = {},
            onApplyClick = {},
            onResetClick = {},
        )
    }
}
