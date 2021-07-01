package github.karchx.motto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.AuthorsStorage
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.parsers.ByAuthorMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthorsViewModel : ViewModel() {

    private val authorsStorage = AuthorsStorage()

    private val _authors = MutableLiveData<ArrayList<Author>>().apply {
        value = authorsStorage.getAuthors()
    }

    private val _authorMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putAuthorMottosPostValue(author: Author) {
        val parser = ByAuthorMottosParser(author)
        GlobalScope.launch {
            _authorMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val authors: LiveData<ArrayList<Author>> = _authors
    val authorMottos: LiveData<ArrayList<Motto>> = _authorMottos
}
