package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.data.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class ByRandomMottosParser : MottosParser {

    override fun getMottos(quantityMottos: Int): ArrayList<Motto> {
        val mottos = ArrayList<Motto>()
        val uriToParse = getUriToParse()
        val okHttp = OkHttpClient()
        val request: Request = Request.Builder().url(uriToParse).get().build()

        for (mottoIndex in 0 until quantityMottos) {
            try {
                val doc: Document = Jsoup.parse(okHttp.newCall(request).execute().body!!.string())
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
