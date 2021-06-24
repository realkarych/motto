package github.karchx.motto.models.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MottoDao {

    @Query("SELECT * FROM motto_table")
    fun getAllMottos(): LiveData<List<Motto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMotto(motto: Motto)

    @Query("DELETE FROM motto_table WHERE quote = :mottoQuote AND source = :mottoSource")
    suspend fun deleteMotto(mottoQuote: String, mottoSource: String)
}
