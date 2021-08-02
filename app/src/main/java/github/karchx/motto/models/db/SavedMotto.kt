package github.karchx.motto.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motto_table")
data class SavedMotto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "quote") val quote: String,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "date_saved") val dateSaved: String
)
