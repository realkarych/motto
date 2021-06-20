package github.karchx.motto.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Topic
import github.karchx.motto.storages.AuthorsStorage
import github.karchx.motto.storages.TopicsStorage

class DashboardViewModel : ViewModel() {

    private val authorsStorage = AuthorsStorage()
    private val topicsStorage = TopicsStorage()

    private val _authors = MutableLiveData<ArrayList<Author>>().apply {
        value = authorsStorage.getAuthors()
    }
    private val _topics = MutableLiveData<ArrayList<Topic>>().apply {
        value = topicsStorage.getTopics()
    }

    val authors: LiveData<ArrayList<Author>> = _authors
    val topics: LiveData<ArrayList<Topic>> = _topics
}
