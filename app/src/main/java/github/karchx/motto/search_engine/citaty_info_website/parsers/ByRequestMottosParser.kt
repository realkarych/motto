package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.storages.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ByRequestMottosParser(private val userRequest: String) : MottosParser {

    override fun getMottos(quantityMottos: Int): ArrayList<Motto> {
        val mottos = ArrayList<Motto>()

        val okHttp = OkHttpClient()
        val request: Request = Request.Builder().url(getUriToParse(userRequest)).get().build()
        val doc: Document = Jsoup.parse(okHttp.newCall(request).execute().body!!.string())
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
        val baseUri = Constants.DOMAIN
        val searchType = Constants.REQUEST_SEARCH_TYPE
        return "$baseUri$searchType$request"
    }
}
