package github.karchx.motto.viewmodels.dashboard.tv_channels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.TVChannelsStorage
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.search_engine.citaty_info_website.items.TVChannel
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByChannelMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVChannelsDashboardViewModel(application: Application, private val prefs: UserPrefs) :
    AndroidViewModel(application) {

    private val tvChannelsStorage = TVChannelsStorage(prefs.sourcesRandomness.isRandom())

    private val _tvChannels = MutableLiveData<ArrayList<TVChannel>>().apply {
        value = tvChannelsStorage.getChannels()
    }

    private val _tvChannelMottos = MutableLiveData<ArrayList<UIMotto>>().apply {}

    fun putTVChannelMottosPostValue(channel: TVChannel) {
        val parser = ByChannelMottosParser(channel, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _tvChannelMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val tvChannels: LiveData<ArrayList<TVChannel>> = _tvChannels
    val tvChannelMottos: LiveData<ArrayList<UIMotto>> = _tvChannelMottos
}
