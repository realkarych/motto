package github.karchx.motto.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.models.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.UIMotto
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByRandomMottosParser
import github.karchx.motto.search_engine.citaty_info_website.parsers.by_sources.ByRequestMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val byRandomParser = ByRandomMottosParser()

    private val _randomMottos = MutableLiveData<ArrayList<UIMotto>>().apply {
        GlobalScope.launch {
            putRandomMottosPostValue()
        }
    }
    private val _requestMottos = MutableLiveData<ArrayList<UIMotto>>().apply {}
    fun putRequestMottosPostValue(request: String) {
        val parser = ByRequestMottosParser(request)
        GlobalScope.launch {
            _requestMottos.postValue(parser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    fun putRandomMottosPostValue() {
        GlobalScope.launch {
            try {
                _randomMottos.postValue(byRandomParser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
            } catch (ex: Exception) {
            }
        }
    }

    val randomMottos: LiveData<ArrayList<UIMotto>> = _randomMottos
    val requestMottos: LiveData<ArrayList<UIMotto>> = _requestMottos
}
