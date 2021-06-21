package github.karchx.motto.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.search_engine.citaty_info_website.data.Author
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.Topic
import github.karchx.motto.search_engine.citaty_info_website.parsers.ByAuthorMottosParser
import github.karchx.motto.search_engine.citaty_info_website.parsers.ByTopicMottosParser
import github.karchx.motto.storages.AuthorsStorage
import github.karchx.motto.storages.Constants
import github.karchx.motto.storages.TopicsStorage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val authorsStorage = AuthorsStorage()
    private val topicsStorage = TopicsStorage()

    private val _authors = MutableLiveData<ArrayList<Author>>().apply {
        value = authorsStorage.getAuthors()
    }
    private val _topics = MutableLiveData<ArrayList<Topic>>().apply {
        value = topicsStorage.getTopics()
    }

    private val _authorMottos = MutableLiveData<ArrayList<Motto>>().apply {}
    fun putAuthorMottosPostValue(author: Author) {
        val parser = ByAuthorMottosParser(author)
        GlobalScope.launch {
            _authorMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    private val _topicMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putTopicMottosPostValue(topic: Topic) {
        val parser = ByTopicMottosParser(topic)
        GlobalScope.launch {
            _topicMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val authors: LiveData<ArrayList<Author>> = _authors
    val topics: LiveData<ArrayList<Topic>> = _topics
    val authorMottos: LiveData<ArrayList<Motto>> = _authorMottos
    val topicMottos: LiveData<ArrayList<Motto>> = _topicMottos
}
