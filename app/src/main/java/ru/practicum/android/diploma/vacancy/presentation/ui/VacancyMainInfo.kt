package ru.practicum.android.diploma.vacancy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.CompanyLogo
import ru.practicum.android.diploma.core.designsystem.components.LightDarkPreviews
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Address
import ru.practicum.android.diploma.core.domain.model.Employer
import ru.practicum.android.diploma.core.domain.model.Employment
import ru.practicum.android.diploma.core.domain.model.Experience
import ru.practicum.android.diploma.core.domain.model.Schedule
import ru.practicum.android.diploma.core.domain.model.VacancyDetails

// Верхняя часть экрана вакансии (макет 28): название и зарплата, карточка работодателя,
// требуемый опыт и строка "занятость, график". Блок без данных скрывается целиком, без пустых отступов.
// Отступы по макету: 24 между блоками, 4 между заголовком и значением, 8 до строки занятости.
@Composable
fun VacancyMainInfo(
    vacancy: VacancyDetails,
    salary: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing24),
    ) {
        VacancyHeader(name = vacancy.name, salary = salary)
        EmployerCard(
            employer = vacancy.employer,
            location = vacancy.address?.raw?.takeIf { it.isNotBlank() } ?: vacancy.area,
        )
        ExperienceAndSchedule(
            experience = vacancy.experience?.name,
            employmentAndSchedule = listOfNotNull(vacancy.employment?.name, vacancy.schedule?.name)
                .filter { it.isNotBlank() }
                .joinToString(separator = ", ")
                .ifBlank { null },
        )
    }
}

@Composable
private fun VacancyHeader(
    name: String,
    salary: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing4)) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge,
            color = DiplomaTheme.colors.primaryText,
        )
        Text(
            text = salary,
            style = MaterialTheme.typography.titleLarge,
            color = DiplomaTheme.colors.primaryText,
        )
    }
}

// Карточка работодателя: цвета Universal, в тёмной теме не инвертируются
@Composable
private fun EmployerCard(
    employer: Employer,
    location: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = DiplomaTheme.colors.cardBackground,
                shape = RoundedCornerShape(Dimens.cornerRadius),
            )
            .padding(Dimens.spacing16),
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
    ) {
        CompanyLogo(logoUrl = employer.logoUrl)
        Column {
            Text(
                text = employer.name,
                style = MaterialTheme.typography.titleLarge,
                color = DiplomaTheme.colors.cardText,
            )
            if (location.isNotBlank()) {
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyLarge,
                    color = DiplomaTheme.colors.cardText,
                )
            }
        }
    }
}

@Composable
private fun ExperienceAndSchedule(
    experience: String?,
    employmentAndSchedule: String?,
) {
    if (experience == null && employmentAndSchedule == null) return
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)) {
        if (experience != null) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing4)) {
                Text(
                    text = stringResource(R.string.vacancy_experience),
                    style = MaterialTheme.typography.titleMedium,
                    color = DiplomaTheme.colors.primaryText,
                )
                VacancyBodyText(text = experience)
            }
        }
        if (employmentAndSchedule != null) {
            VacancyBodyText(text = employmentAndSchedule)
        }
    }
}

@Composable
private fun VacancyBodyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = DiplomaTheme.colors.primaryText,
    )
}

@LightDarkPreviews
@Composable
private fun VacancyMainInfoPreview() {
    DiplomaTheme {
        Column(
            modifier = Modifier
                .background(DiplomaTheme.colors.background)
                .padding(Dimens.screenHorizontalPadding),
        ) {
            VacancyMainInfo(
                vacancy = VacancyDetails(
                    id = "1",
                    name = "Android-разработчик",
                    description = "",
                    salary = null,
                    address = Address(
                        id = "1",
                        city = "Москва",
                        street = "Льва Толстого",
                        building = "16",
                        raw = "Москва, Льва Толстого, 16",
                    ),
                    experience = Experience(id = "between1And3", name = "От 1 года до 3 лет"),
                    schedule = Schedule(id = "remote", name = "Удаленная работа"),
                    employment = Employment(id = "full", name = "Полная занятость"),
                    contacts = null,
                    employer = Employer(id = "1", name = "Еда", logoUrl = null),
                    area = "Москва",
                    skills = emptyList(),
                    shareUrl = "",
                    industryName = "",
                ),
                salary = "от 100 000 ₽",
            )
        }
    }
}
