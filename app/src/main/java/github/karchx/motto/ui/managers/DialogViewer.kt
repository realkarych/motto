package github.karchx.motto.ui.managers

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.data.Motto

class DialogViewer {
    companion object {
        fun displayFullMottoDialog(dialog: Dialog, clickedMotto: Motto) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            dialog.show()

            val tvFullMottoQuote = dialog.findViewById<TextView>(R.id.textview_motto_full_quote)
            val tvFullMottoSource = dialog.findViewById<TextView>(R.id.textview_motto_full_source)
            tvFullMottoQuote.text = clickedMotto.quote
            tvFullMottoSource.text = clickedMotto.source
        }
    }
}
