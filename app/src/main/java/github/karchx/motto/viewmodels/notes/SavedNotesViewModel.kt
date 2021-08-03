package github.karchx.motto.viewmodels.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import github.karchx.motto.models.UserNoteRepository
import github.karchx.motto.models.db.MottoDatabase
import github.karchx.motto.models.db.user_notes.UserNote
import kotlinx.coroutines.launch

class SavedNotesViewModel(application: Application) : AndroidViewModel(application) {

    private val database =
        MottoDatabase.getDatabase(getApplication<Application>().applicationContext)
    private val repository = UserNoteRepository(database.userNoteDao())

    val allNotes: LiveData<List<UserNote>> = repository.allNotes

    fun insertNote(note: UserNote) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteNote(noteQuote: String, noteSource: String) = viewModelScope.launch {
        repository.removeNote(noteQuote, noteSource)
    }

    fun updateNote(
        oldNoteQuote: String,
        oldNoteSource: String,
        noteQuote: String,
        noteSource: String
    ) = viewModelScope.launch {
        repository.updateNote(oldNoteQuote, oldNoteSource, noteQuote, noteSource)
    }
}
