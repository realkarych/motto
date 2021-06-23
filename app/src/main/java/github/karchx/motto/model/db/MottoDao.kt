package github.karchx.motto.model.db

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

    @Query("DELETE FROM motto_table WHERE id = :mottoID")
    suspend fun deleteMotto(mottoID: Int)
}
