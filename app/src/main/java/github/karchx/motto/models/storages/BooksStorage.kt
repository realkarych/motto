package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.data.Book

class BooksStorage {

    fun getBooks(): ArrayList<Book> {
        val books = ArrayList<Book>()

        books.shuffle()
        return books
    }
}