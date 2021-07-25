package github.karchx.motto.models.storages

import android.content.Context
import github.karchx.motto.R

class Constants {
    companion object {
        const val DOMAIN = "https://citaty.info/"
        const val REQUEST_SEARCH_TYPE = "search/site/"
        const val ARTICLE_ROOT_ELEMENT_NAME = "article"
        const val MOTTOS_SORT_TYPE = "?sort_by=rating"
        const val QUANTITY_MOTTOS_IN_LIST = 64
        const val QUANTITY_WORDS_IN_MOTTO_TITLE = 12

        val MOTTO_TYPES_ICONS =
            arrayListOf(
                R.drawable.ic_baseline_book_24,
                R.drawable.ic_baseline_camera_24,
                R.drawable.ic_baseline_serials_24,
                R.drawable.ic_baseline_anime_24,
                R.drawable.ic_baseline_topic_24,
                R.drawable.ic_baseline_person_24,
                R.drawable.ic_baseline_channels_24
            )

        fun getMottoTypesNames(context: Context): ArrayList<String> {
            return arrayListOf(
                context.getString(R.string.books),
                context.getString(R.string.films),
                context.getString(R.string.series),
                context.getString(R.string.anime),
                context.getString(R.string.topics),
                context.getString(R.string.authors),
                context.getString(R.string.channels)
            )
        }

        const val LAST_TAB_SELECTED = "selected_tab"
        const val NUMBER_OF_DISPLAYED_ADS = "num_of_displayed_ads"
        const val IS_COPY_WITH_AUTHOR = "is_copy_with_author"
    }
}
