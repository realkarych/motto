package github.karchx.motto.ui.managers

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import github.karchx.motto.R

class Copier {
    companion object {
        fun getMottoDataToCopy(context: Context, quote: String, source: String): String {
            return "\"$quote\"\n\n" + "${context.getString(R.string.motto_source)} $source"
        }

        fun copyText(activity: Activity, text: String) {
            val clipboard: ClipboardManager? =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("stub", text)
            clipboard?.setPrimaryClip(clip)
        }
    }
}
