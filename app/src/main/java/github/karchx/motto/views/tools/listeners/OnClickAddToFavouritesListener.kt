package github.karchx.motto.views.tools.listeners

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import github.karchx.motto.R
import github.karchx.motto.models.date.DateManager
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.viewmodels.SavedMottosViewModel
import github.karchx.motto.views.tools.managers.Vibrator

class OnClickAddToFavouritesListener {

    companion object {

        fun handleMotto(
            context: Context,
            savedMottosViewModel: SavedMottosViewModel,
            addToFavouritesImageView: ImageView,
            allDbMottos: List<SavedMotto>,
            clickedMotto: UIMotto
        ) {
            val allQuotes = ArrayList<String>()
            val allSources = ArrayList<String>()
            for (motto in allDbMottos) {
                allQuotes.add(motto.quote)
                allSources.add(motto.source)
            }

            if (clickedMotto.quote in allQuotes && clickedMotto.source in allSources) {
                savedMottosViewModel.deleteMotto(clickedMotto.quote, clickedMotto.source)
                addToFavouritesImageView.setImageDrawable(getNotAddedMottoIcon(context))
            } else {
                val currentDate = DateManager().getCurrentDate()

                savedMottosViewModel.insertMotto(
                    SavedMotto(
                        0,
                        clickedMotto.quote,
                        clickedMotto.source,
                        currentDate
                    )
                )

                Vibrator().setLowVibration(context)
                addToFavouritesImageView.setImageDrawable(getAddedMottoIcon(context))
            }
        }

        private fun getNotAddedMottoIcon(context: Context): Drawable? {
            return AppCompatResources.getDrawable(
                context,
                R.drawable.ic_favorite_border
            )
        }

        private fun getAddedMottoIcon(context: Context): Drawable? {
            return AppCompatResources.getDrawable(
                context,
                R.drawable.ic_favorite
            )
        }
    }
}
