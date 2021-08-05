package github.karchx.motto.models.user_settings

import android.content.SharedPreferences
import github.karchx.motto.models.storages.Constants

class NoteSaves(private val prefs: SharedPreferences) {

    // True - мод сохранения
    // False - мод update-а
    fun setNoteMode(isSave: Boolean) {
        prefs.edit().putBoolean(Constants.IS_SAVE_NOTE, isSave).apply()
    }

    // True - мод сохранения
    // False - мод update-а
    fun noteSaveMode(): Boolean {
        return prefs.getBoolean(Constants.IS_SAVE_NOTE, true)
    }
}