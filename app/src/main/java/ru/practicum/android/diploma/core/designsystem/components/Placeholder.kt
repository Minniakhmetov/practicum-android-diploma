package ru.practicum.android.diploma.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme

// Плейсхолдер пустых состояний и ошибок: иллюстрация 328x223 по центру, под ней текст.
// Текста может не быть (начальный экран поиска). Содержимое центрируется в доступной области,
// поэтому экран обычно передаёт Modifier.fillMaxSize().
@Composable
fun Placeholder(
    @DrawableRes image: Int,
    text: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(image),
            // Иллюстрация декоративная, смысл передаёт текст под ней
            contentDescription = null,
            modifier = Modifier.size(
                width = Dimens.placeholderImageWidth,
                height = Dimens.placeholderImageHeight,
            ),
        )
        if (text != null) {
            Text(
                text = text,
                modifier = Modifier.padding(top = Dimens.placeholderTextTopPadding),
                style = MaterialTheme.typography.titleLarge,
                color = DiplomaTheme.colors.primaryText,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@LightDarkPreviews
@Composable
private fun PlaceholderPreview() {
    ComponentPreview {
        Placeholder(image = R.drawable.img_search_start_328x223, text = null)
        Placeholder(image = R.drawable.img_no_internet_328x223, text = "Нет интернета")
        Placeholder(
            image = R.drawable.img_empty_list_328x223,
            text = "Не удалось получить список вакансий",
        )
    }
}
