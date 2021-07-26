package github.karchx.motto.models.user_settings

import android.content.SharedPreferences
import github.karchx.motto.models.storages.Constants

class CopySettings(private val prefs: SharedPreferences) {

    fun isWithSource(): Boolean {
        return prefs.getBoolean(Constants.IS_COPY_WITH_SOURCE, true)
    }

    fun updateState() {
        val isCopyWithAuthor = CopySettings(prefs).isWithSource()
        if (isCopyWithAuthor) {
            prefs.edit().putBoolean(Constants.IS_COPY_WITH_SOURCE, false).apply()
        } else {
            prefs.edit().putBoolean(Constants.IS_COPY_WITH_SOURCE, true).apply()
        }
    }
}