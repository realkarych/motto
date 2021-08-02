package github.karchx.motto.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import github.karchx.motto.models.MottoRepository
import github.karchx.motto.models.db.SavedMotto
import github.karchx.motto.models.db.MottoDatabase
import kotlinx.coroutines.launch

class MottosViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val database = MottoDatabase.getDatabase(context)
    private val repository = MottoRepository(database.mottoDao())

    val allMottos: LiveData<List<SavedMotto>> = repository.allMottos

    fun insertMotto(motto: SavedMotto) = viewModelScope.launch {
        repository.insertMotto(motto)
    }

    fun deleteMotto(mottoQuote: String, mottoSource: String) = viewModelScope.launch {
        repository.removeMotto(mottoQuote, mottoSource)
    }
}
