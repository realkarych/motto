package github.karchx.motto.viewmodels.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.models.storages.TVSeriesStorage
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.data.TVSeries
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.BySeriesMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TVSeriesDashboardViewModel : ViewModel() {

    private val tvSeriesStorage = TVSeriesStorage()

    private val _tvSeries = MutableLiveData<ArrayList<TVSeries>>().apply {
        value = tvSeriesStorage.getSeries()
    }

    private val _tvSeriesMottos = MutableLiveData<ArrayList<Motto>>().apply {}

    fun putTVSeriesMottosPostValue(series: TVSeries) {
        val parser = BySeriesMottosParser(series)
        GlobalScope.launch {
            _tvSeriesMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val tvSeries: LiveData<ArrayList<TVSeries>> = _tvSeries
    val tvSeriesMottos: LiveData<ArrayList<Motto>> = _tvSeriesMottos
}
