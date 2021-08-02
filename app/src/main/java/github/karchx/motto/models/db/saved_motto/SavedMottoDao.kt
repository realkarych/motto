package github.karchx.motto.models.db.saved_motto

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedMottoDao {

    @Query("SELECT * FROM saved_motto_table")
    fun getAllMottos(): LiveData<List<SavedMotto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMotto(motto: SavedMotto)

    @Query("DELETE FROM saved_motto_table WHERE quote = :mottoQuote AND source = :mottoSource")
    suspend fun deleteMotto(mottoQuote: String, mottoSource: String)
}
