package github.karchx.motto.viewmodels.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.TVChannelsStorage
import github.karchx.motto.search_engine.citaty_info_website.items.Motto
import github.karchx.motto.search_engine.citaty_info_website.items.TVChannel
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByChannelMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVChannelsDashboardViewModel : ViewModel() {

    private val tvChannelsStorage = TVChannelsStorage()

    private val _tvChannels = MutableLiveData<ArrayList<TVChannel>>().apply {
        value = tvChannelsStorage.getChannels()
    }

    private val _tvChannelMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putTVChannelMottosPostValue(channel: TVChannel) {
        val parser = ByChannelMottosParser(channel)
        GlobalScope.launch {
            _tvChannelMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val tvChannels: LiveData<ArrayList<TVChannel>> = _tvChannels
    val tvChannelMottos: LiveData<ArrayList<Motto>> = _tvChannelMottos
}
