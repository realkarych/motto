package github.karchx.motto.models.user_settings

import android.content.SharedPreferences
import github.karchx.motto.models.storages.Constants

class RateApp(private val prefs: SharedPreferences) {

    fun updateCounter(): Long {
        val counterValue = prefs.getLong(Constants.RATE_APP_COUNTER, 0)
        prefs.edit().putLong(Constants.RATE_APP_COUNTER, counterValue+1).apply()
        return counterValue + 1
    }
}