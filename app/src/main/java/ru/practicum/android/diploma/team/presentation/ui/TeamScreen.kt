package ru.practicum.android.diploma.team.presentation.ui

import android.content.Context
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.components.AppTopBar
import ru.practicum.android.diploma.core.designsystem.components.LightDarkPreviews
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

private class TeamMember(
    @StringRes val nameRes: Int,
    val githubUrl: String,
)

// Участники команды: статичные данные, поэтому ViewModel экрану не нужна
private val teamMembers = listOf(
    TeamMember(R.string.team_member_aleksandra, "https://github.com/sasha-zadiraka"),
    TeamMember(R.string.team_member_florid, "https://github.com/Minniakhmetov"),
)

// Экран "Команда" (макет 46): TopBar, заголовок Bold 32 и список имён Medium 16.
// Отступы по макету: 24 от TopBar до заголовка, 32 от заголовка до первого имени, 16 между именами.
// Имя открывает GitHub участника. При открытии экрана поверх него проигрывается фейерверк.
// Нижнюю панель навигации показывает RootActivity, экран добавляет только отступ под статус-бар.
@Composable
fun TeamScreen(
    modifier: Modifier = Modifier,
    playFireworks: Boolean = true,
) {
    val context = LocalContext.current
    // Фейерверк не показываем, если в системе отключены анимации
    var showFireworks by remember {
        mutableStateOf(playFireworks && areAnimationsEnabled(context))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background),
    ) {
        TeamContent(modifier = Modifier.statusBarsPadding())
        if (showFireworks) {
            Fireworks(
                onFinished = { showFireworks = false },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun TeamContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.team),
            onBackClick = null,
            backContentDescription = null,
        )
        // Список прокручивается, если участников станет больше, чем помещается на экране
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = Dimens.spacing24, bottom = Dimens.spacing16),
        ) {
            Text(
                text = stringResource(R.string.team_title),
                // 24 + отступ строки имени 8 = 32 до первого имени по макету
                modifier = Modifier.padding(
                    start = Dimens.screenHorizontalPadding,
                    end = Dimens.screenHorizontalPadding,
                    bottom = Dimens.spacing24,
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = DiplomaTheme.colors.primaryText,
            )
            teamMembers.forEach { member ->
                TeamMemberRow(member = member)
            }
        }
    }
}

// Строка на всю ширину с отступами 8 сверху и снизу: между текстами соседних имён 16, как в макете,
// а нажимать удобнее, чем на одну строку текста.
// Имя - ссылка, поэтому оно акцентного цвета, как и другие ссылки в приложении (email, телефон).
// В макете имена цвета основного текста: отступили от него, чтобы было видно, что имя кликабельно
@Composable
private fun TeamMemberRow(member: TeamMember) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = stringResource(member.nameRes),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClickLabel = stringResource(R.string.team_open_github)) {
                // Браузера может не быть на устройстве: тогда просто ничего не открываем
                runCatching { uriHandler.openUri(member.githubUrl) }
            }
            .padding(horizontal = Dimens.screenHorizontalPadding, vertical = Dimens.spacing8),
        style = MaterialTheme.typography.titleMedium,
        color = DiplomaTheme.colors.accent,
    )
}

private fun areAnimationsEnabled(context: Context): Boolean =
    Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) > 0f

@LightDarkPreviews
@Composable
private fun TeamScreenPreview() {
    DiplomaTheme {
        TeamScreen(playFireworks = false)
    }
}
