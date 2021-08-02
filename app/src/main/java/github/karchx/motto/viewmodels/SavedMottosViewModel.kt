package github.karchx.motto.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import github.karchx.motto.models.SavedMottoRepository
import github.karchx.motto.models.db.saved_motto.SavedMotto
import github.karchx.motto.models.db.MottoDatabase
import kotlinx.coroutines.launch

class SavedMottosViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MottoDatabase.getDatabase(getApplication<Application>().applicationContext)
    private val repository = SavedMottoRepository(database.savedMottoDao())

    val allMottos: LiveData<List<SavedMotto>> = repository.allMottos

    fun insertMotto(motto: SavedMotto) = viewModelScope.launch {
        repository.insertMotto(motto)
    }

    fun deleteMotto(mottoQuote: String, mottoSource: String) = viewModelScope.launch {
        repository.removeMotto(mottoQuote, mottoSource)
    }
}
