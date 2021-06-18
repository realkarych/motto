package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.data.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class ByRequestMottosParser(private val userRequest: String) : MottosParser {

    override fun getMottos(quantityMottos: Int): ArrayList<Motto> {
        val mottos = ArrayList<Motto>()

        val doc = Jsoup.connect(getUriToParse(userRequest)).get()
        val articles: Elements = doc.select(Constants.ARTICLE_ROOT_ELEMENT_NAME)

        val limitedMottosQuantity = getLimitedMottosQuantity(articles, quantityMottos)

        for (mottoIndex in 0 until limitedMottosQuantity) {
            try {
                mottos.add(HtmlMottosParser.getMottoFromHtml(doc, mottoIndex))
            } catch (ex: Exception) {
            }
        }
        return mottos
    }

    private fun getLimitedMottosQuantity(articles: Elements, quantityMottos: Int): Int {
        return if (articles.size < quantityMottos) articles.size
        else quantityMottos
    }

    private fun getUriToParse(request: String): String {
        val baseUrl = Constants.DOMAIN
        val searchType = Constants.REQUEST_SEARCH_TYPE
        return "$baseUrl$searchType$request"
    }
}
