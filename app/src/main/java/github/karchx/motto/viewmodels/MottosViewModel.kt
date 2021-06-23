package github.karchx.motto.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import github.karchx.motto.model.MottoRepository
import github.karchx.motto.model.db.Motto
import github.karchx.motto.model.db.MottoDatabase
import kotlinx.coroutines.launch

class MottosViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val database = MottoDatabase.getDatabase(context)
    private val repository = MottoRepository(database.mottoDao())

    val allMottos: LiveData<List<Motto>> = repository.allMottos

    fun insertMotto(motto: Motto) = viewModelScope.launch {
        repository.insertMotto(motto)
    }

    fun deleteMotto(mottoID: Int) = viewModelScope.launch {
        repository.removeMotto(mottoID)
    }
}
