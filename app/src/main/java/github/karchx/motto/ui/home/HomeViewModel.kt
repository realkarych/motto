package github.karchx.motto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import github.karchx.motto.storages.Constants
import github.karchx.motto.search_engine.citaty_info_website.data.Motto
import github.karchx.motto.search_engine.citaty_info_website.parsers.ByRandomMottosParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val byRandomParser = ByRandomMottosParser()

    private val _randomMottos = MutableLiveData<ArrayList<Motto>>().apply {
        GlobalScope.launch {
            putRandomMottosPostValue()
        }
    }

    fun putRandomMottosPostValue() {
        GlobalScope.launch {
            _randomMottos.postValue(byRandomParser.getMottos(Constants.QUANTITY_MOTTOS_IN_LIST))
        }
    }

    val randomMottos: LiveData<ArrayList<Motto>> = _randomMottos
}
