package ru.practicum.android.diploma.vacancy.domain.sharing

// Действия во внешних приложениях: поделиться, написать письмо, позвонить.
// Реализация в data, чтобы ViewModel и интерактор не работали с Intent и Context
interface ExternalNavigator {
    fun shareLink(url: String)
    fun sendEmail(email: String)
    fun dialPhone(phone: String)
}
