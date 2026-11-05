package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Лоадер: синее кольцо 36 в рамке 48
@Composable
fun AppLoader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(Dimens.loaderSize),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimens.loaderIndicatorSize),
            color = DiplomaTheme.colors.accent,
            trackColor = Color.Transparent,
        )
    }
}

@LightDarkPreviews
@Composable
private fun AppLoaderPreview() {
    ComponentPreview {
        AppLoader()
    }
}
