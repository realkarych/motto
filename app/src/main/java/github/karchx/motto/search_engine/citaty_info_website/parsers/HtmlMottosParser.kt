package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import org.jsoup.nodes.Element

class HtmlMottosParser {

    companion object {

        fun getMottoFromHtml(articlesElement: Element, mottoIndex: Int): Motto {
            val parser = HtmlMottosParser()
            val rootContentElement = parser.getRootContentElement(articlesElement, mottoIndex)

            val isSaved = false
            val quote: String = parser.getQuoteElement(rootContentElement)?.text() ?: ""
            val source: String = try {
                parser.getSourceElement(rootContentElement)?.text() ?: ""
            } catch (ex: NullPointerException) {
                ""
            }

            return Motto(quote, source, isSaved)
        }
    }

    private fun getRootContentElement(articlesElement: Element, mottoIndex: Int): Element? {
        val cssQuery = ".node__content"
        return articlesElement.select(cssQuery)[mottoIndex]
    }

    private fun getQuoteElement(rootContentElement: Element?): Element? {
        val cssQuery = ".field-item.even.last"
        return rootContentElement?.select(cssQuery)?.first()
    }

    private fun getSourceElement(rootContentElement: Element?): Element? {
        val cssQueryRoot = "div.field-item.even"
        val cssQueryDeep = "a[title]"
        return rootContentElement?.select(cssQueryRoot)?.select(cssQueryDeep)?.first()
    }
}
