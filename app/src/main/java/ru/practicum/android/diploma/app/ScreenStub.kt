package ru.practicum.android.diploma.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Временные заглушки экранов (#41-#43) для проверки навигации.
// Удаляется, когда во всех фичах появятся настоящие экраны.

internal const val STUB_VACANCY_ID = "0000347f-1440-3f6f-a91a-d52c15a41021"

internal class StubButton(val title: String, val onClick: () -> Unit)

// ComposeView с заглушкой для onCreateView фрагмента
internal fun Fragment.screenStubView(
    title: String,
    buttons: List<StubButton>,
): ComposeView =
    ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DiplomaTheme {
                ScreenStub(title = title, buttons = buttons)
            }
        }
    }

@Composable
private fun ScreenStub(
    title: String,
    buttons: List<StubButton>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DiplomaTheme.colors.background)
            .safeDrawingPadding()
            .padding(Dimens.screenHorizontalPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.spacing24),
            style = MaterialTheme.typography.titleLarge,
            color = DiplomaTheme.colors.primaryText,
            textAlign = TextAlign.Center,
        )
        buttons.forEach { button ->
            Button(
                onClick = button.onClick,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = button.title)
            }
        }
    }
}
