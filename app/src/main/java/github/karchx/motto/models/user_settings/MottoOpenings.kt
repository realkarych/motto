package github.karchx.motto.models.user_settings

import android.content.SharedPreferences
import github.karchx.motto.models.storages.Constants

class MottoOpenings(private val prefs: SharedPreferences) {

    fun getNumberOfOpens(): Int {
        return prefs.getInt(Constants.NUMBER_OF_DISPLAYED_ADS, 0)
    }

    fun updateNumberOfOpens() {
        val opensNumber = MottoOpenings(prefs).getNumberOfOpens()
        prefs.edit().putInt(Constants.NUMBER_OF_DISPLAYED_ADS, opensNumber + 1).apply()
    }

    fun resetNumberOfOpens() {
        prefs.edit().putInt(Constants.NUMBER_OF_DISPLAYED_ADS, 0).apply()
    }
}