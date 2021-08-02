package github.karchx.motto.models.db.user_notes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserNoteDao {

    @Query("SELECT * FROM user_note_table")
    fun getAllNotes(): LiveData<List<UserNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: UserNote)

    @Query("DELETE FROM user_note_table WHERE quote = :noteQuote AND source = :noteSource")
    suspend fun deleteNote(noteQuote: String, noteSource: String)

    @Query("UPDATE user_note_table SET quote = :noteQuote, source = :noteSource WHERE quote = :oldNoteQuote AND source = :oldNoteSource")
    suspend fun updateNote(
        oldNoteQuote: String,
        oldNoteSource: String,
        noteQuote: String,
        noteSource: String
    )
}