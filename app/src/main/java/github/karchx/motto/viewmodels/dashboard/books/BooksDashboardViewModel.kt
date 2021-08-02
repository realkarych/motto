package github.karchx.motto.viewmodels.dashboard.books

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.BooksStorage
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.items.Book
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByBookMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BooksDashboardViewModel(application: Application, private val prefs: UserPrefs) :
    AndroidViewModel(application) {

    private val booksStorage = BooksStorage(prefs.sourcesRandomness.isRandom())

    private val _books = MutableLiveData<ArrayList<Book>>().apply {
        value = booksStorage.getBooks()
    }

    private val _bookMottos = MutableLiveData<ArrayList<UIMotto>>().apply {}

    fun putBookMottosPostValue(book: Book) {
        val parser = ByBookMottosParser(book, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _bookMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val books: LiveData<ArrayList<Book>> = _books
    val bookMottos: LiveData<ArrayList<UIMotto>> = _bookMottos
}
