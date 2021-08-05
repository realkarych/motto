package github.karchx.motto.views.tools.managers

import android.content.Context
import android.widget.Toast
import github.karchx.motto.R

class Toaster {
    companion object {
        fun displayTextIsCopiedToast(context: Context) {
            val text = context.getString(R.string.motto_copied)
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context.applicationContext, text, duration)
            toast.show()
        }

        fun displayNoteAddedToast(context: Context, isAdded: Boolean) {
            var text: String = if (isAdded) {
                context.getString(R.string.note_added)
            } else {
                context.getString(R.string.note_not_added)
            }

            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context.applicationContext, text, duration)
            toast.show()
        }

        fun displayNoteUpdatedToast(context: Context, isUpdated: Boolean) {
            var text: String = if (isUpdated) {
                context.getString(R.string.note_updated)
            } else {
                context.getString(R.string.note_not_updated)
            }

            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context.applicationContext, text, duration)
            toast.show()
        }
    }
}
