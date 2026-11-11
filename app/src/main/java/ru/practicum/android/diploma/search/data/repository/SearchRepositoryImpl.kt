package ru.practicum.android.diploma.search.data.repository

import ru.practicum.android.diploma.core.common.result.Resource
import ru.practicum.android.diploma.core.domain.model.FilterSettings
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.search.data.api.SearchApi
import ru.practicum.android.diploma.search.data.mapper.toDomain
import ru.practicum.android.diploma.search.domain.model.VacanciesPage
import ru.practicum.android.diploma.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun searchVacancies(
        text: String,
        settings: FilterSettings,
        page: Int
    ): Resource<VacanciesPage> {
        val options = createSearchOptions(text, settings, page)
        val result = networkClient.execute {
            searchApi.searchVacancies(options)
        }
        return when (result) {
            is Resource.Success -> Resource.Success(result.data.toDomain())
            is Resource.Error -> result
        }
    }

    // Сборка параметров запроса
    // Если фильтр "пустой" (все опциональные поля null/false), в Map попадут только text, page - запрос эквивалентен поиску без фильтра
    private fun createSearchOptions(
        text: String,
        settings: FilterSettings,
        page: Int
    ): Map<String, String> = buildMap {
        put(KEY_TEXT, text.trim())
        put(KEY_PAGE, page.toString())
        settings.cityId?.let { put(KEY_AREA, it.toString()) }
        settings.industryId?.let { put(KEY_INDUSTRY, it.toString()) }
        settings.salary?.let { put(KEY_SALARY, it.toString()) }

        // only_with_salary: добавляем только при true.
        // false — это "не фильтровать", а не "фильтровать по false", поэтому параметр не отправляем
        if (settings.onlyWithSalary) {
            put(KEY_ONLY_WITH_SALARY, "true")
        }
    }

    private companion object {
        const val KEY_TEXT = "text"
        const val KEY_PAGE = "page"
        const val KEY_AREA = "area"
        const val KEY_INDUSTRY = "industry"
        const val KEY_SALARY = "salary"
        const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }
}
