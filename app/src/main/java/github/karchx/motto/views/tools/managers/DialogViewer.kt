package github.karchx.motto.views.tools.managers

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.items.Motto
import github.karchx.motto.models.db.Motto as dbMotto


class DialogViewer {
    companion object {
        fun displayFullMottoDialog(
            context: Context,
            dialog: Dialog,
            clickedMotto: Motto,
            allDbMottos: List<dbMotto>
        ) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.9f
            dialog.window!!.attributes = lp
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialog.setCancelable(true)
            dialog.show()

            val tvFullMottoQuote = dialog.findViewById<TextView>(R.id.textview_motto_full_quote)
            val tvFullMottoSource = dialog.findViewById<TextView>(R.id.textview_motto_full_source)
            val ivAddToFavourites = dialog.findViewById<ImageView>(R.id.imageview_is_saved_motto)
            tvFullMottoQuote.text = clickedMotto.quote
            tvFullMottoSource.text = clickedMotto.source

            val allQuotes = ArrayList<String>()
            val allSources = ArrayList<String>()
            for (motto in allDbMottos) {
                allQuotes.add(motto.quote)
                allSources.add(motto.source)
            }

            if (clickedMotto.quote in allQuotes && clickedMotto.source in allSources) {
                ivAddToFavourites.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_favorite
                    )
                )
                ivAddToFavourites.visibility = View.VISIBLE
            } else {
                ivAddToFavourites.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_favorite_border
                    )
                )
                ivAddToFavourites.visibility = View.VISIBLE
            }
        }
    }
}
