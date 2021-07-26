package github.karchx.motto.viewmodels.dashboard.anime

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.AnimeStorage
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.items.Anime
import github.karchx.motto.search_engine.citaty_info_website.items.Motto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByAnimeMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AnimeDashboardViewModel(application: Application, private val prefs: UserPrefs) : AndroidViewModel(application) {

    private val animeStorage = AnimeStorage(prefs.sourcesRandomness.isRandom())

    private val _anime = MutableLiveData<ArrayList<Anime>>().apply {
        value = animeStorage.getAnime()
    }

    private val _animeMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putAnimeMottosPostValue(anime: Anime) {
        val parser = ByAnimeMottosParser(anime, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _animeMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val anime: LiveData<ArrayList<Anime>> = _anime
    val animeMottos: LiveData<ArrayList<Motto>> = _animeMottos
}
