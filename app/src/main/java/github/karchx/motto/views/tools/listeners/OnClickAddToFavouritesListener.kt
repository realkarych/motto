package github.karchx.motto.views.tools.listeners

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import github.karchx.motto.R
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.viewmodels.MottosViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import github.karchx.motto.models.db.Motto as dbMotto

class OnClickAddToFavouritesListener {

    companion object {

        fun handleMotto(
            context: Context,
            mottosViewModel: MottosViewModel,
            addToFavouritesImageView: ImageView,
            allDbMottos: List<dbMotto>,
            clickedMotto: Motto
        ) {
            val allQuotes = ArrayList<String>()
            val allSources = ArrayList<String>()
            for (motto in allDbMottos) {
                allQuotes.add(motto.quote)
                allSources.add(motto.source)
            }

            if (clickedMotto.quote in allQuotes && clickedMotto.source in allSources) {
                mottosViewModel.deleteMotto(clickedMotto.quote, clickedMotto.source)
                addToFavouritesImageView.setImageDrawable(getNotAddedMottoIcon(context))
            } else {
                val currentDate = getCurrentDate()

                mottosViewModel.insertMotto(
                    dbMotto(
                        0,
                        clickedMotto.quote,
                        clickedMotto.source,
                        currentDate
                    )
                )

                setVibration(context)
                addToFavouritesImageView.setImageDrawable(getAddedMottoIcon(context))
            }
        }

        private fun getCurrentDate(): String {
            return SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault()).format(Date())
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

        private fun setVibration(context: Context) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                v.vibrate(50)
            }
        }
    }
}
