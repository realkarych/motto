package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.items.Motto
import org.jsoup.nodes.Element

class HtmlMottosParser {

    companion object {

        fun getMottoFromHtml(articlesElement: Element, mottoIndex: Int): Motto {
            val parser = HtmlMottosParser()
            val rootContentElement = parser.getRootContentElement(articlesElement, mottoIndex)

            val quote: String = parser.getQuoteElement(rootContentElement)?.text() ?: ""
            val source: String = try {
                parser.getSourceElement(rootContentElement)?.text() ?: "Автор неизвестен"
            } catch (ex: NullPointerException) {
                "Автор неизвестен"
            }
            return Motto(reformatQuote(quote), source)
        }

        private fun reformatQuote(quote: String): String {
            var counter = 0
            var reformattedQuote = ""

            for (word in quote) {
                if (word == '—') {
                    if (counter != 0) {
                        reformattedQuote += "\n$word"
                    } else {
                        reformattedQuote += word
                    }
                    counter += 1
                } else {
                    reformattedQuote += word
                }
            }

            return reformattedQuote
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
