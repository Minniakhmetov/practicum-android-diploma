package ru.practicum.android.diploma.vacancy.data.sharing

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.net.toUri
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.vacancy.domain.sharing.ExternalNavigator

// Все действия открываются через Intent.createChooser: критерии требуют предлагать выбор приложения,
// а без chooser при одном подходящем приложении выбора не будет. Если подходящих приложений нет,
// системный диалог сам сообщит об этом, а ActivityNotFoundException ловим на всякий случай.
// Context - приложения, поэтому нужен FLAG_ACTIVITY_NEW_TASK
class ExternalNavigatorImpl(
    private val context: Context,
) : ExternalNavigator {

    override fun shareLink(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = MIME_TEXT
            putExtra(Intent.EXTRA_TEXT, url)
        }
        startChooser(intent, R.string.vacancy_share)
    }

    // ACTION_SENDTO с mailto: - только почтовые клиенты, без мессенджеров
    override fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO, "mailto:$email".toUri())
        startChooser(intent, R.string.vacancy_send_email)
    }

    // ACTION_DIAL только открывает номер в звонилке, разрешение CALL_PHONE не нужно.
    // Номер приходит со скобками, пробелами и дефисами: в tel: оставляем только цифры и +
    override fun dialPhone(phone: String) {
        val number = phone.filter { it.isDigit() || it == '+' }
        val intent = Intent(Intent.ACTION_DIAL, "tel:$number".toUri())
        startChooser(intent, R.string.vacancy_call)
    }

    private fun startChooser(intent: Intent, @StringRes titleRes: Int) {
        val chooser = Intent.createChooser(intent, context.getString(titleRes))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(chooser)
        } catch (exception: ActivityNotFoundException) {
            Log.w(TAG, "No activity for ${intent.action}", exception)
        }
    }

    private companion object {
        const val TAG = "ExternalNavigator"
        const val MIME_TEXT = "text/plain"
    }
}
