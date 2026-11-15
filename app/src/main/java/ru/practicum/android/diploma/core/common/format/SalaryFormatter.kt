package ru.practicum.android.diploma.core.common.format

import androidx.annotation.StringRes
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.resources.ResourceProvider
import ru.practicum.android.diploma.core.domain.model.Salary
import java.util.Locale

class SalaryFormatter(
    private val resourceProvider: ResourceProvider
) {
    fun format(salary: Salary?, @StringRes emptyTextResId: Int): String {
        val from = salary?.from
        val to = salary?.to
        val currency = salary?.currency
        return when {
            from != null && to != null -> resourceProvider.getString(
                R.string.salary_from_to,
                formatAmount(from),
                withCurrency(formatAmount(to), currency)
            )

            from != null -> resourceProvider.getString(
                R.string.salary_from,
                withCurrency(formatAmount(from), currency)
            )

            to != null -> resourceProvider.getString(
                R.string.salary_to,
                withCurrency(formatAmount(to), currency)
            )

            else -> resourceProvider.getString(emptyTextResId)
        }
    }

    private fun formatAmount(amount: Int): String {
        return String.format(Locale.US, "%,d", amount).replace(",", NBSP)
    }

    private fun withCurrency(amount: String, currency: String?): String {
        val symbol = currency?.let { CURRENCY_SYMBOLS[it] ?: it }
        return if (symbol.isNullOrBlank()) amount else "$amount$NBSP$symbol"
    }

    private companion object {
        const val NBSP = "\u00A0"

        val CURRENCY_SYMBOLS = mapOf(
            "RUR" to "₽",
            "RUB" to "₽",
            "BYR" to "Br",
            "USD" to "$",
            "EUR" to "€",
            "KZT" to "₸",
            "UAH" to "₴",
            "AZN" to "₼",
            "UZS" to "so'm",
            "GEL" to "₾",
            "KGT" to "сом",
            "KGS" to "сом"
        )
    }
}
