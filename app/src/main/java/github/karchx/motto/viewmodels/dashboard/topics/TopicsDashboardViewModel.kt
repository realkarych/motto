package github.karchx.motto.viewmodels.dashboard.topics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.TopicsStorage
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.items.Motto
import github.karchx.motto.search_engine.citaty_info_website.items.Topic
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByTopicMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TopicsDashboardViewModel(application: Application, private val prefs: UserPrefs) : AndroidViewModel(application) {

    private val topicsStorage = TopicsStorage(prefs.sourcesRandomness.isRandom())

    private val _topics = MutableLiveData<ArrayList<Topic>>().apply {
        value = topicsStorage.getTopics()
    }

    private val _topicMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putTopicMottosPostValue(topic: Topic) {
        val parser = ByTopicMottosParser(topic, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _topicMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val topics: LiveData<ArrayList<Topic>> = _topics
    val topicMottos: LiveData<ArrayList<Motto>> = _topicMottos
}
