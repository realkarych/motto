package github.karchx.motto.models.user_settings

import android.content.SharedPreferences
import github.karchx.motto.models.storages.Constants

class MottosRandomness(private val prefs: SharedPreferences) {

    fun isRandom(): Boolean {
        return prefs.getBoolean(Constants.IS_SORT_MOTTOS_BY_RANDOM, false)
    }

    fun updateState() {
        val isRandom = MottosRandomness(prefs).isRandom()
        if (isRandom) {
            prefs.edit().putBoolean(Constants.IS_SORT_MOTTOS_BY_RANDOM, false).apply()
        } else {
            prefs.edit().putBoolean(Constants.IS_SORT_MOTTOS_BY_RANDOM, true).apply()
        }
    }
}