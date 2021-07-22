package github.karchx.motto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.AnimeStorage
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Anime
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByAnimeMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AnimeDashboardViewModel : ViewModel() {

    private val animeStorage = AnimeStorage()

    private val _anime = MutableLiveData<ArrayList<Anime>>().apply {
        value = animeStorage.getAnime()
    }

    private val _animeMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putAnimeMottosPostValue(anime: Anime) {
        val parser = ByAnimeMottosParser(anime)
        GlobalScope.launch {
            _animeMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val anime: LiveData<ArrayList<Anime>> = _anime
    val animeMottos: LiveData<ArrayList<Motto>> = _animeMottos
}
