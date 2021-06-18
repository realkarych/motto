package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.data.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import org.jsoup.Jsoup

class ByRandomMottosParser : MottosParser {

    override fun getMottos(quantityMottos: Int): ArrayList<Motto> {
        val mottos = ArrayList<Motto>()
        val uriToParse = getUriToParse()

        for (mottoIndex in 0 until quantityMottos) {
            try {
                mottos.add(getMottoFromHtml(uriToParse))
            } catch (ex: Exception) {
            }
        }

        return mottos
    }

    private fun getMottoFromHtml(uriToParse: String): Motto {
        val doc = Jsoup.connect(uriToParse).get()

        val quote: String = doc.select(".field-item.even.last").text()
        val source: String = doc.select(".field-item.even")[1].text()
        // TODO: Fix this stub after db integration
        val isSaved = false

        return Motto(quote, source, isSaved)
    }

    private fun getUriToParse(): String {
        val baseUrl = Constants.DOMAIN
        val searchType = Constants.RANDOM_SEARCH_TYPE
        return "$baseUrl$searchType"
    }
}
