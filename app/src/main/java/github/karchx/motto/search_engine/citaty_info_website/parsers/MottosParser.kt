package github.karchx.motto.search_engine.citaty_info_website.parsers

import github.karchx.motto.search_engine.citaty_info_website.UIMotto

interface MottosParser {
    fun getMottos(quantityMottos: Int): ArrayList<UIMotto>
}
