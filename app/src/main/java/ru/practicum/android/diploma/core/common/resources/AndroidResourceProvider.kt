package ru.practicum.android.diploma.core.common.resources

import android.content.Context

class AndroidResourceProvider(
    private val context: Context
) : ResourceProvider {
    override fun getString(resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }
}
