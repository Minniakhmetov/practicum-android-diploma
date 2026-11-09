package ru.practicum.android.diploma.vacancy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.LightDarkPreviews
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.core.domain.model.Contacts
import ru.practicum.android.diploma.core.domain.model.Phone

// Нижняя часть экрана вакансии (макет 28): описание, ключевые навыки, контакты.
// Отступы по макету: 16 от заголовка раздела до содержимого и между подразделами,
// 4 от подзаголовка до текста, пункты списка идут без отступов
@Composable
fun VacancyDescription(
    html: String,
    modifier: Modifier = Modifier,
) {
    val blocks = remember(html) { DescriptionHtmlParser.parse(html) }
    if (blocks.isEmpty()) return
    Column(modifier = modifier.fillMaxWidth()) {
        // В API заголовок раздела приходит в самом HTML. Если его нет - добавляем свой
        if (blocks.first() !is DescriptionBlock.Title) {
            SectionTitle(text = stringResource(R.string.vacancy_description))
            Spacer(modifier = Modifier.height(Dimens.spacing16))
        }
        blocks.forEachIndexed { index, block ->
            if (index > 0) {
                Spacer(modifier = Modifier.height(spacingBetween(blocks[index - 1], block)))
            }
            DescriptionBlockText(block)
        }
    }
}

private fun spacingBetween(previous: DescriptionBlock, current: DescriptionBlock): Dp = when {
    previous is DescriptionBlock.ListItem && current is DescriptionBlock.ListItem -> 0.dp
    previous is DescriptionBlock.Subtitle -> Dimens.spacing4
    else -> Dimens.spacing16
}

@Composable
private fun DescriptionBlockText(block: DescriptionBlock) {
    val text = remember(block.html) { AnnotatedString.fromHtml(block.html) }
    when (block) {
        is DescriptionBlock.Title -> SectionTitle(text = text)
        is DescriptionBlock.Subtitle -> VacancyText(text = text, style = MaterialTheme.typography.titleMedium)
        is DescriptionBlock.Paragraph -> VacancyText(text = text)
        is DescriptionBlock.ListItem -> BulletItem(text = text)
    }
}

// Раздел скрыт, если навыков нет
@Composable
fun VacancySkills(
    skills: List<String>,
    modifier: Modifier = Modifier,
) {
    val visibleSkills = skills.filter { it.isNotBlank() }
    if (visibleSkills.isEmpty()) return
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing16),
    ) {
        SectionTitle(text = stringResource(R.string.vacancy_key_skills))
        Column {
            visibleSkills.forEach { BulletItem(text = AnnotatedString(it)) }
        }
    }
}

// Раздел скрыт, если нет ни контактного лица, ни email, ни телефонов.
// Email и телефоны - синие и кликабельные, сами переходы делает ExternalNavigator (#68)
@Composable
fun VacancyContacts(
    contacts: Contacts?,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val name = contacts?.name?.takeIf { it.isNotBlank() }
    val email = contacts?.email?.takeIf { it.isNotBlank() }
    val phones = contacts?.phones.orEmpty().filter { it.formatted.isNotBlank() }
    if (name == null && email == null && phones.isEmpty()) return
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing16),
    ) {
        SectionTitle(text = stringResource(R.string.vacancy_contacts))
        if (name != null) {
            ContactItem(label = stringResource(R.string.vacancy_contact_person)) {
                VacancyText(text = AnnotatedString(name))
            }
        }
        if (email != null) {
            ContactItem(label = stringResource(R.string.vacancy_email)) {
                LinkText(text = email, onClick = { onEmailClick(email) })
            }
        }
        phones.forEach { phone ->
            PhoneItem(phone = phone, onClick = { onPhoneClick(phone.formatted) })
        }
    }
}

// Комментарий к телефону - под номером, обычным текстом
@Composable
private fun PhoneItem(
    phone: Phone,
    onClick: () -> Unit,
) {
    ContactItem(label = stringResource(R.string.vacancy_phone)) {
        LinkText(text = phone.formatted, onClick = onClick)
        phone.comment?.takeIf { it.isNotBlank() }?.let { VacancyText(text = AnnotatedString(it)) }
    }
}

@Composable
private fun ContactItem(
    label: String,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing4)) {
        VacancyText(text = AnnotatedString(label), style = MaterialTheme.typography.titleMedium)
        content()
    }
}

@Composable
private fun LinkText(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = Modifier.clickable(role = Role.Button, onClick = onClick),
        style = MaterialTheme.typography.bodyLarge,
        color = DiplomaTheme.colors.accent,
    )
}

@Composable
private fun SectionTitle(text: String) {
    SectionTitle(text = AnnotatedString(text))
}

@Composable
private fun SectionTitle(text: AnnotatedString) {
    VacancyText(text = text, style = MaterialTheme.typography.titleLarge)
}

// Маркер отдельным Text: перенесенные строки пункта выравниваются по тексту, а не по маркеру
@Composable
private fun BulletItem(text: AnnotatedString) {
    Row {
        VacancyText(
            text = AnnotatedString(BULLET),
            modifier = Modifier.padding(end = Dimens.spacing8),
        )
        VacancyText(text = text)
    }
}

@Composable
private fun VacancyText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = DiplomaTheme.colors.primaryText,
    )
}

private const val BULLET = "•"

@LightDarkPreviews
@Composable
private fun VacancyDetailsSectionsPreview() {
    DiplomaTheme {
        Column(
            modifier = Modifier
                .background(DiplomaTheme.colors.background)
                .padding(Dimens.screenHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing24),
        ) {
            VacancyDescription(
                html = "<h2>Описание вакансии</h2><p>Ищем Android-разработчика.</p>" +
                    "<section><h3>Обязанности</h3><ul><li>Разрабатывать новую функциональность</li>" +
                    "<li>Писать UI- и unit-тесты</li></ul></section>",
            )
            VacancySkills(skills = listOf("Kotlin", "Jetpack Compose", "Coroutines"))
            VacancyContacts(
                contacts = Contacts(
                    id = "1",
                    name = "Ирина",
                    email = "i.lozgkina@yandex.ru",
                    phones = listOf(
                        Phone(comment = null, formatted = "+7 (904) 329-27-71"),
                        Phone(comment = "Звонить после 10:00", formatted = "+7 (999) 123-45-67"),
                    ),
                ),
                onEmailClick = {},
                onPhoneClick = {},
            )
        }
    }
}
