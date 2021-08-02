package github.karchx.motto.viewmodels.dashboard.films

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.FilmsStorage
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.items.Film
import github.karchx.motto.search_engine.citaty_info_website.items.UIMotto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByFilmMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FilmsDashboardViewModel(application: Application, private val prefs: UserPrefs) :
    AndroidViewModel(application) {

    private val filmsStorage = FilmsStorage(prefs.sourcesRandomness.isRandom())

    private val _films = MutableLiveData<ArrayList<Film>>().apply {
        value = filmsStorage.getFilms()
    }

    private val _filmMottos = MutableLiveData<ArrayList<UIMotto>>().apply {}

    fun putFilmMottosPostValue(film: Film) {
        val parser = ByFilmMottosParser(film, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _filmMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val films: LiveData<ArrayList<Film>> = _films
    val filmMottos: LiveData<ArrayList<UIMotto>> = _filmMottos
}
