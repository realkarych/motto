package github.karchx.motto.viewmodels.dashboard.authors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.AuthorsStorage
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.items.Author
import github.karchx.motto.search_engine.citaty_info_website.items.UIMotto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByAuthorMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthorsDashboardViewModel(application: Application, private val prefs: UserPrefs) :
    AndroidViewModel(application) {

    private val authorsStorage = AuthorsStorage(prefs.sourcesRandomness.isRandom())

    private val _authors = MutableLiveData<ArrayList<Author>>().apply {
        value = authorsStorage.getAuthors()
    }

    private val _authorMottos = MutableLiveData<ArrayList<UIMotto>>().apply {}

    fun putAuthorMottosPostValue(author: Author) {
        val parser = ByAuthorMottosParser(author, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _authorMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val authors: LiveData<ArrayList<Author>> = _authors
    val authorMottos: LiveData<ArrayList<UIMotto>> = _authorMottos
}
