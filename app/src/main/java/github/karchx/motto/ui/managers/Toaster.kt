package github.karchx.motto.ui.managers

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
    }
}
