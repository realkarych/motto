package github.karchx.motto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.TopicsStorage
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.Topic
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByTopicMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TopicsDashboardViewModel : ViewModel() {

    private val topicsStorage = TopicsStorage()

    private val _topics = MutableLiveData<ArrayList<Topic>>().apply {
        value = topicsStorage.getTopics()
    }

    private val _topicMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putTopicMottosPostValue(topic: Topic) {
        val parser = ByTopicMottosParser(topic)
        GlobalScope.launch {
            _topicMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val topics: LiveData<ArrayList<Topic>> = _topics
    val topicMottos: LiveData<ArrayList<Motto>> = _topicMottos
}
