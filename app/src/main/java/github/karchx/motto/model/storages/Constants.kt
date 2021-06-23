package github.karchx.motto.model.storages

import github.karchx.motto.R

class Constants {
    companion object {
        const val DOMAIN = "https://citaty.info/"
        const val REQUEST_SEARCH_TYPE = "search/site/"
        const val ARTICLE_ROOT_ELEMENT_NAME = "article"
        const val MOTTOS_SORT_TYPE = "?sort_by=rating"
        const val QUANTITY_MOTTOS_IN_LIST = 40
        const val QUANTITY_WORDS_IN_MOTTO_TITLE = 12
        val MOTTO_TYPES_NAMES = arrayListOf("Authors", "Topics")
        val MOTTO_TYPES_ICONS =
            arrayListOf(R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_topic_24)
        const val KEYWORD_MOTTO_TYPE = "motto_type"
    }
}
