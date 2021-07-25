package github.karchx.motto.copying

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import github.karchx.motto.R
import github.karchx.motto.views.MainActivity

class Copier(private val activity: MainActivity, private val context: Context) {

    fun getMottoDataToCopy(quote: String, source: String, isCopyWithAuthor: Boolean): String {
        if (isCopyWithAuthor) {
            return "\"$quote\"\n" + "${context.getString(R.string.motto_source)} $source"
        }
        return quote
    }

    fun copyText(text: String) {
        val clipboard: ClipboardManager? =
            activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("stub", text)
        clipboard?.setPrimaryClip(clip)
    }
}
