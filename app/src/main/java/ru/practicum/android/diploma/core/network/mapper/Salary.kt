package ru.practicum.android.diploma.core.network.mapper

import ru.practicum.android.diploma.core.domain.model.Salary
import ru.practicum.android.diploma.core.network.dto.SalaryDto

fun SalaryDto.toDomain(): Salary {
    return Salary(
        from = from,
        to = to,
        currency = currency
    )
}
