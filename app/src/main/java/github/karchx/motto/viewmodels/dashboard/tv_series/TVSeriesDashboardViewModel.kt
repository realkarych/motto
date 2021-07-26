package github.karchx.motto.viewmodels.dashboard.tv_series

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.TVSeriesStorage
import github.karchx.motto.models.user_settings.UserPrefs
import github.karchx.motto.search_engine.citaty_info_website.items.Motto
import github.karchx.motto.search_engine.citaty_info_website.items.TVSeries
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.BySeriesMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVSeriesDashboardViewModel(application: Application, private val prefs: UserPrefs) :
    AndroidViewModel(application) {

    private val tvSeriesStorage = TVSeriesStorage(prefs.sourcesRandomness.isRandom())

    private val _tvSeries = MutableLiveData<ArrayList<TVSeries>>().apply {
        value = tvSeriesStorage.getSeries()
    }

    private val _tvSeriesMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putTVSeriesMottosPostValue(series: TVSeries) {
        val parser = BySeriesMottosParser(series, prefs.mottosRandomness.isRandom())
        GlobalScope.launch {
            _tvSeriesMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val tvSeries: LiveData<ArrayList<TVSeries>> = _tvSeries
    val tvSeriesMottos: LiveData<ArrayList<Motto>> = _tvSeriesMottos
}
