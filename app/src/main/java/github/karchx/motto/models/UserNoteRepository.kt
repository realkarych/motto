package github.karchx.motto.models

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import github.karchx.motto.models.db.user_notes.UserNote
import github.karchx.motto.models.db.user_notes.UserNoteDao

class UserNoteRepository(private val userNoteDao: UserNoteDao) {

    val allNotes: LiveData<List<UserNote>> = userNoteDao.getAllNotes()

    @WorkerThread
    suspend fun insertNote(note: UserNote) {
        userNoteDao.insertNote(note)
    }

    @WorkerThread
    suspend fun removeNote(noteQuote: String, noteSource: String) {
        userNoteDao.deleteNote(noteQuote, noteSource)
    }

    @WorkerThread
    suspend fun updateNote(
        oldNoteQuote: String,
        oldNoteSource: String,
        noteQuote: String,
        noteSource: String
    ) {
        userNoteDao.updateNote(oldNoteQuote, oldNoteSource, noteQuote, noteSource)
    }
}