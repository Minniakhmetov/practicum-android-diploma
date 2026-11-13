package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Карточка вакансии в списке поиска и избранного (экраны 9 и 44):
// логотип, справа заголовок "Название, Город" (Medium 22), работодатель и зарплата (Regular 16).
// Зарплата приходит готовой строкой: её форматирует SalaryFormatter у вызывающего экрана,
// пустой случай везде одинаковый: "Зарплата не указана".
@Composable
fun VacancyListItem(
    name: String,
    area: String?,
    employerName: String?,
    salary: String,
    logoUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = Dimens.screenHorizontalPadding,
                vertical = Dimens.vacancyItemVerticalPadding,
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12),
    ) {
        CompanyLogo(logoUrl = logoUrl)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vacancyTitle(name = name, area = area),
                style = MaterialTheme.typography.titleLarge,
                color = DiplomaTheme.colors.primaryText,
            )
            if (!employerName.isNullOrBlank()) {
                VacancyListItemText(text = employerName)
            }
            VacancyListItemText(text = salary)
        }
    }
}

@Composable
private fun VacancyListItemText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = DiplomaTheme.colors.primaryText,
    )
}

private fun vacancyTitle(name: String, area: String?): String =
    if (area.isNullOrBlank()) name else "$name, $area"

@LightDarkPreviews
@Composable
private fun VacancyListItemPreview() {
    ComponentPreview {
        VacancyListItem(
            name = "Android-разработчик",
            area = "Москва",
            employerName = "Яндекс",
            salary = "от 100 000 ₽",
            logoUrl = null,
            onClick = {},
        )
        VacancyListItem(
            name = "Ведущий Android-разработчик в команду платёжных сервисов",
            area = "Санкт-Петербург",
            employerName = "Очень длинное название компании-работодателя",
            salary = "Зарплата не указана",
            logoUrl = null,
            onClick = {},
        )
    }
}
