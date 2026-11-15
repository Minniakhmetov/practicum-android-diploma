package ru.practicum.android.diploma.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.designsystem.theme.Dimens
import ru.practicum.android.diploma.core.designsystem.theme.DiplomaTheme
import ru.practicum.android.diploma.core.designsystem.theme.White

// Логотип с соотношением сторон в этих пределах считаем квадратным
private const val SQUARE_MIN_ASPECT_RATIO = 0.9f
private const val SQUARE_MAX_ASPECT_RATIO = 1.1f

// Логотип работодателя 48x48: скругление 12, рамка 1 цвета Light Gray (в обеих темах).
// Пока логотип грузится, при ошибке и если ссылки нет - плейсхолдер 32 по центру (отступ 8).
// Используется в карточке вакансии в списке и в карточке работодателя на экране вакансии.
@Composable
fun CompanyLogo(
    logoUrl: String?,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(Dimens.cornerRadius)
    // Плейсхолдер виден, пока картинка не загрузилась успешно
    var isLoaded by remember(logoUrl) { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(Dimens.logoSize)
            .clip(shape)
            .border(width = Dimens.strokeWidth, color = DiplomaTheme.colors.divider, shape = shape),
        contentAlignment = Alignment.Center,
    ) {
        if (logoUrl != null) {
            LogoImage(
                logoUrl = logoUrl,
                isLoaded = isLoaded,
                onLoadedChange = { isLoaded = it },
            )
        }
        if (!isLoaded) {
            Image(
                painter = painterResource(R.drawable.ic_logo_placeholder_32),
                contentDescription = null,
                modifier = Modifier.size(Dimens.logoPlaceholderIconSize),
            )
        }
    }
}

// Квадратный логотип занимает всю рамку, как в макете.
// Остальные вписываются целиком с отступом: и широкие текстовые ("Яндекс Маркет", "IBM"),
// и неквадратные значки (птичка Twitter, яблоко Apple). Иначе Crop срезает края,
// а без отступа края упираются в рамку и срезаются скруглением.
@Composable
private fun LogoImage(
    logoUrl: String,
    isLoaded: Boolean,
    onLoadedChange: (Boolean) -> Unit,
) {
    // Меняется только при успешной загрузке: смена contentScale перезапускает запрос,
    // и сброс флага в состоянии Loading привёл бы к бесконечному переключению
    var isSquare by remember(logoUrl) { mutableStateOf(true) }

    AsyncImage(
        model = logoUrl,
        // Логотип декоративный: название компании выводится рядом текстом
        contentDescription = null,
        // Логотипы компаний рассчитаны на светлый фон: без белой подложки
        // чёрные логотипы (Apple, Яндекс) не видны в тёмной теме
        modifier = Modifier
            .fillMaxSize()
            .then(if (isLoaded) Modifier.background(White) else Modifier)
            .then(if (isSquare) Modifier else Modifier.padding(Dimens.spacing4)),
        contentScale = if (isSquare) ContentScale.Crop else ContentScale.Fit,
        onState = { state ->
            val success = state as? AsyncImagePainter.State.Success
            if (success != null) {
                val image = success.result.image
                val aspectRatio = image.width.toFloat() / image.height
                isSquare = aspectRatio in SQUARE_MIN_ASPECT_RATIO..SQUARE_MAX_ASPECT_RATIO
            }
            onLoadedChange(success != null)
        },
    )
}

@LightDarkPreviews
@Composable
private fun CompanyLogoPreview() {
    ComponentPreview {
        Row {
            CompanyLogo(logoUrl = null)
        }
    }
}
