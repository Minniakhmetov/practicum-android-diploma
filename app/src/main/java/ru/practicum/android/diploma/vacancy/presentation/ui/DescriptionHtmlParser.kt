package ru.practicum.android.diploma.vacancy.presentation.ui

// Блок описания вакансии. html - содержимое блока: может содержать строчные теги (b, i, br)
// и HTML-сущности, их разбирает AnnotatedString.fromHtml при отрисовке
sealed interface DescriptionBlock {
    val html: String

    // h1, h2: заголовок раздела (в API всегда приходит <h2>Описание вакансии</h2>)
    data class Title(override val html: String) : DescriptionBlock

    // h3-h6: подзаголовок ("Обязанности", "Требования", "Условия")
    data class Subtitle(override val html: String) : DescriptionBlock
    data class Paragraph(override val html: String) : DescriptionBlock
    data class ListItem(override val html: String) : DescriptionBlock
}

// Делит HTML описания на блоки, чтобы экран расставил отступы по макету:
// AnnotatedString.fromHtml сам по себе не дает управлять отступами между заголовками и списками.
// Теги разбираются регуляркой: от API приходит простой HTML без вложенных блоков внутри li и p
object DescriptionHtmlParser {

    private val tagRegex = Regex("<(/?)([a-zA-Z][a-zA-Z0-9]*)[^>]*>")
    private val anyTagRegex = Regex("<[^>]*>")
    private val titleTags = setOf("h1", "h2")
    private val subtitleTags = setOf("h3", "h4", "h5", "h6")

    // Теги, которые начинают или заканчивают блок. Остальные (b, i, br, a...) остаются внутри блока
    private val blockTags = titleTags + subtitleTags + setOf("p", "li", "ul", "ol", "div", "section")

    fun parse(html: String): List<DescriptionBlock> {
        val blocks = mutableListOf<DescriptionBlock>()
        val buffer = StringBuilder()
        var currentTag: String? = null

        fun flush() {
            val content = buffer.toString().trim()
            buffer.clear()
            if (content.replace(anyTagRegex, "").isBlank()) return
            blocks += when (currentTag) {
                in titleTags -> DescriptionBlock.Title(content)
                in subtitleTags -> DescriptionBlock.Subtitle(content)
                "li" -> DescriptionBlock.ListItem(content)
                else -> DescriptionBlock.Paragraph(content)
            }
        }

        var position = 0
        tagRegex.findAll(html).forEach { match ->
            buffer.append(html, position, match.range.first)
            position = match.range.last + 1
            val tag = match.groupValues[2].lowercase()
            if (tag in blockTags) {
                flush()
                val isClosing = match.groupValues[1].isNotEmpty()
                currentTag = if (isClosing) null else tag
            } else {
                buffer.append(match.value)
            }
        }
        buffer.append(html, position, html.length)
        flush()
        return blocks
    }
}
