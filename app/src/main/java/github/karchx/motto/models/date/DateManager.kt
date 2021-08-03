package github.karchx.motto.models.date

import java.text.SimpleDateFormat
import java.util.*

class DateManager {
    fun getCurrentDate(): String {
        return SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault()).format(Date())
    }
}