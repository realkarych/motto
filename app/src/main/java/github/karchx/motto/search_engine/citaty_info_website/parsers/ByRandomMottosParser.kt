package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.data.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import org.jsoup.Jsoup

class ByRandomMottosParser : MottosParser {

    override fun getMottos(quantityMottos: Int): ArrayList<Motto> {
        val mottos = ArrayList<Motto>()
        val uriToParse = getUriToParse()
        val connection = Jsoup.connect(uriToParse)

        for (mottoIndex in 0 until quantityMottos) {
            try {
                val doc = connection.get()
                mottos.add(HtmlMottosParser.getMottoFromHtml(doc, 0))
            } catch (ex: Exception) {
            }
        }

        return mottos
    }

    private fun getUriToParse(): String {
        val baseUri = Constants.DOMAIN
        val searchType = Constants.RANDOM_SEARCH_TYPE
        return "$baseUri$searchType"
    }
}
