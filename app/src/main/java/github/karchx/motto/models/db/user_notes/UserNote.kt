package github.karchx.motto.models.db.user_notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_note_table")
data class UserNote(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "quote") val quote: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "date_saved") val dateSaved: String
)