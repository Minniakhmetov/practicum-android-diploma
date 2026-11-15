package ru.practicum.android.diploma.core.common.format

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.common.resources.ResourceProvider
import ru.practicum.android.diploma.core.domain.model.Salary

class SalaryFormatterTest {

    private val formatter = SalaryFormatter(FakeResourceProvider())

    @Test
    fun `only from - shows from with currency`() {
        val result = formatter.format(Salary(from = 50_000, to = null, currency = "RUR"), EMPTY_SEARCH)

        assertEquals("от 50${NBSP}000$NBSP₽", result)
    }

    @Test
    fun `only to - shows to with currency`() {
        val result = formatter.format(Salary(from = null, to = 80_000, currency = "RUR"), EMPTY_SEARCH)

        assertEquals("до 80${NBSP}000$NBSP₽", result)
    }

    @Test
    fun `from and to - shows range with currency once at the end`() {
        val result = formatter.format(Salary(from = 50_000, to = 80_000, currency = "RUR"), EMPTY_SEARCH)

        assertEquals("от 50${NBSP}000 до 80${NBSP}000$NBSP₽", result)
    }

    @Test
    fun `no salary - shows empty text passed as parameter`() {
        assertEquals("Зарплата не указана", formatter.format(null, EMPTY_SEARCH))
        assertEquals("Уровень зарплаты не указан", formatter.format(null, EMPTY_DETAILS))
    }

    @Test
    fun `no bounds - shows empty text even if currency is present`() {
        val result = formatter.format(Salary(from = null, to = null, currency = "RUR"), EMPTY_DETAILS)

        assertEquals("Уровень зарплаты не указан", result)
    }

    @Test
    fun `amount is split into groups of three digits with non-breaking spaces`() {
        assertEquals("от 0", formatter.format(Salary(from = 0, to = null, currency = null), EMPTY_SEARCH))
        assertEquals("от 999", formatter.format(Salary(from = 999, to = null, currency = null), EMPTY_SEARCH))
        assertEquals("от 1${NBSP}000", formatter.format(Salary(from = 1_000, to = null, currency = null), EMPTY_SEARCH))
        assertEquals(
            "от 1${NBSP}000${NBSP}000",
            formatter.format(Salary(from = 1_000_000, to = null, currency = null), EMPTY_SEARCH)
        )
    }

    @Test
    fun `known currencies are shown as symbols`() {
        val expected = mapOf(
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

        expected.forEach { (code, symbol) ->
            val result = formatter.format(Salary(from = 100, to = null, currency = code), EMPTY_SEARCH)
            assertEquals("currency $code", "от 100$NBSP$symbol", result)
        }
    }

    @Test
    fun `unknown currency is shown as code`() {
        val result = formatter.format(Salary(from = 100, to = null, currency = "XYZ"), EMPTY_SEARCH)

        assertEquals("от 100${NBSP}XYZ", result)
    }

    @Test
    fun `missing currency - shows amount only`() {
        assertEquals("до 100", formatter.format(Salary(from = null, to = 100, currency = null), EMPTY_SEARCH))
        assertEquals("до 100", formatter.format(Salary(from = null, to = 100, currency = ""), EMPTY_SEARCH))
    }

    private class FakeResourceProvider : ResourceProvider {
        private val strings = mapOf(
            R.string.salary_from to "от %1\$s",
            R.string.salary_to to "до %1\$s",
            R.string.salary_from_to to "от %1\$s до %2\$s",
            EMPTY_SEARCH to "Зарплата не указана",
            EMPTY_DETAILS to "Уровень зарплаты не указан"
        )

        override fun getString(resId: Int, vararg formatArgs: Any): String {
            return strings.getValue(resId).format(*formatArgs)
        }
    }

    private companion object {
        const val NBSP = "\u00A0"
        val EMPTY_SEARCH = R.string.salary_not_specified
        const val EMPTY_DETAILS = -1
    }
}
