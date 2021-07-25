package github.karchx.motto.models.user_settings

import android.content.Context
import github.karchx.motto.views.MainActivity

class UserPrefs(activity: MainActivity) {
    private val userPrefs = activity.getPreferences(Context.MODE_PRIVATE)

    val mottoOpenings = MottoOpenings(userPrefs)
    val copySettings = CopySettings(userPrefs)
    val mottosRandomness = MottosRandomness(userPrefs)
    val sourcesRandomness = SourcesRandomness(userPrefs)
}