package github.karchx.motto.views.tools.managers

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.play.core.review.ReviewManagerFactory
import github.karchx.motto.R
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.models.db.user_notes.UserNote
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.views.tools.firebase_events.MottoLikedEvent
import github.karchx.motto.views.tools.firebase_events.MottoOpenedEvent
import github.karchx.motto.views.tools.firebase_events.NoteOpenedEvent


class DialogViewer {
    companion object {
        fun displayFullMottoDialog(
            activity: Activity,
            context: Context,
            dialog: Dialog,
            clickedMotto: UIMotto,
            allDbMottos: List<SavedMotto>
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

                pushMottoLikedEvent()
            } else {
                ivAddToFavourites.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_favorite_border
                    )
                )
                ivAddToFavourites.visibility = View.VISIBLE
            }

            displayRateAppIntent(activity, context)

            pushMottoOpenedEvent()
        }

        fun displayFullNoteDialog(
            dialog: Dialog,
            clickedNote: UserNote
        ) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.9f
            dialog.window!!.attributes = lp
            dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialog.setCancelable(true)
            dialog.show()

            val tvFullMottoQuote = dialog.findViewById<TextView>(R.id.textview_note_full_quote)
            val tvFullMottoSource = dialog.findViewById<TextView>(R.id.textview_note_full_source)
            tvFullMottoQuote.text = clickedNote.quote
            tvFullMottoSource.text = clickedNote.source

            pushNoteOpenedEvent()
        }

        private fun pushMottoOpenedEvent() {
            val mottoOpenedEvent = MottoOpenedEvent()
            mottoOpenedEvent.pushEvent()
        }

        private fun pushNoteOpenedEvent() {
            val noteOpenedEvent = NoteOpenedEvent()
            noteOpenedEvent.pushEvent()
        }

        private fun pushMottoLikedEvent() {
            val mottoLikedEvent = MottoLikedEvent()
            mottoLikedEvent.pushEvent()
        }

        fun displayRateAppIntent(activity: Activity, context: Context) {
            val manager = ReviewManagerFactory.create(context)
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    // We got the ReviewInfo object
                    val reviewInfo = request.result
                    val flow = manager.launchReviewFlow(activity, reviewInfo)
                    flow.addOnCompleteListener { _ ->
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                    }
                } else {
                    // There was some problem, continue regardless of the result.
                }
            }
        }
    }
}
