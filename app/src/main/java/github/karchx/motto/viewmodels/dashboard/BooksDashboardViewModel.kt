package github.karchx.motto.viewmodels.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.BooksStorage
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Book
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByBookMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BooksDashboardViewModel : ViewModel() {

    private val booksStorage = BooksStorage()

    private val _books = MutableLiveData<ArrayList<Book>>().apply {
        value = booksStorage.getBooks()
    }

    private val _bookMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putBookMottosPostValue(book: Book) {
        val parser = ByBookMottosParser(book)
        GlobalScope.launch {
            _bookMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val books: LiveData<ArrayList<Book>> = _books
    val bookMottos: LiveData<ArrayList<Motto>> = _bookMottos
}
